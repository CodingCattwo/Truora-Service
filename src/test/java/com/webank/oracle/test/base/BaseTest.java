package com.webank.oracle.test.base;

import java.math.BigInteger;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.webank.oracle.Application;
import com.webank.oracle.base.properties.EventRegisterProperties;
import com.webank.oracle.base.service.Web3jMapService;
import com.webank.oracle.base.utils.CryptoUtil;
import com.webank.oracle.contract.ContractDeployRepository;
import com.webank.oracle.history.ReqHistoryRepository;
import com.webank.oracle.keystore.KeyStoreService;
import com.webank.oracle.transaction.register.OracleRegisterCenterService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@SpringBootTest(classes = Application.class)
public class BaseTest {
    @Autowired protected Web3jMapService web3jMapService;
    @Autowired protected EventRegisterProperties eventRegisterProperties;
    @Autowired protected KeyStoreService keyStoreService;
    @Autowired protected ContractDeployRepository contractDeployRepository;
    @Autowired protected ReqHistoryRepository reqHistoryRepository;
    @Autowired protected OracleRegisterCenterService oracleRegisterCenterService;

    //根据私钥导入账户
    protected Credentials credentials;
    protected Credentials credentialsBob = Credentials.create("2");

    // 生成随机私钥使用下面方法；
    // Credentials credentialsBob =Credentials.create(Keys.createEcKeyPair());
    protected String Bob = "0x2b5ad5c4795c026514f8317c7a215e218dccd6cf";
    protected String Owner = "0x148947262ec5e21739fe3a931c29e8b84ee34a0f";

    protected String Alice = "0x1abc9fd9845cd5a0acefa72e4f40bcfd4136f864";

//    @Autowired
//    private Flyway flyway;

//    @BeforeEach
//    public void setUp()   {
//
//      //  flyway.setBaselineOnMigrate(true);
//        flyway.clean();
//        flyway.migrate();
//    }
    
    protected Web3j getWeb3j(int chainId, int groupId){
         return web3jMapService.getNotNullWeb3j(chainId,groupId);
    }


    public static byte[] calculateTheHashOfPK(String skhex) {
        Credentials user = Credentials.create(skhex);
        // gm address  0x1f609497612656e806512fb90972d720e2e508b5
        //   address   0xc950b511a1a6a1241fc53d5692fdcbed4f766c65
        String pk = user.getEcKeyPair().getPublicKey().toString(16);

        int len = pk.length();
        String pkx = pk.substring(0,len/2);
        String pky = pk.substring(len/2);
        BigInteger Bx = new BigInteger(pkx,16);
        BigInteger By = new BigInteger(pky,16);

        return CryptoUtil.soliditySha3(Bx,By);
    }


}