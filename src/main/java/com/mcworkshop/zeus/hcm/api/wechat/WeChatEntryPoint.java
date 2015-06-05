package com.mcworkshop.zeus.hcm.api.wechat;

import me.chanjar.weixin.cp.api.*;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by markfredchen on 6/4/15.
 */
@Controller
@RequestMapping(value = "/weChat")
public class WeChatEntryPoint {
    protected WxCpConfigStorage wxCpConfigStorage;
    protected WxCpService wxCpService;
    protected WxCpMessageRouter wxCpMessageRouter;

    public WeChatEntryPoint(){
        WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
        config.setCorpId("wxe4aff45f5aba161b");
        config.setCorpSecret("Ib8lN6ZCLoxwjNe3oyoeszwsy-mXyPBRamgXCddytqNfZ6ZRx2LZpVzQGZCwwBlw");
        config.setAgentId("1");
        config.setToken("ZEUSHCM");
        config.setAesKey("YsY3st3zVCoCQFC04am4C8nOShirIofGDHJSaDaSPjj");
        wxCpConfigStorage = config;
        wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpConfigStorage);

    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String validate(
            @RequestParam("msg_signature") String msg_signature,
            @RequestParam("nonce") String nonce,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("echostr") String echostr) {
        System.out.println("ENTER");
        if(wxCpService.checkSignature(msg_signature, timestamp, nonce, echostr)){
            WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
            String plainText = cryptUtil.decrypt(echostr);
            return plainText;
        }else {
            return "error";
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody String hello() {
        return "Test";
    }
}
