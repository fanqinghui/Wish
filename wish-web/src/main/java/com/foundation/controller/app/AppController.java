package com.foundation.controller.app;

import com.foundation.biz.app.IAppBiz;
import com.foundation.biz.vo.InitPo;
import com.foundation.common.BaseController;
import com.foundation.common.Contants;
import com.foundation.common.bean.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/app/")
public class AppController extends BaseController {

    Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    IAppBiz appBiz;

    /**
     * 初始化接口，返回版本等信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "init",method = {RequestMethod.GET})
    @ResponseBody
    public ResultVo init(HttpServletRequest request) throws Exception {
        ResultVo result = new ResultVo();
        try{
            String typeS=getReqValByParam("type");
            String versionS=getReqValByParam("version");
            if(StringUtils.isBlank(typeS)|| StringUtils.isBlank(versionS)){
                result.setTip(Contants.PARAMMISTIP);
            }else{
                InitPo vo=appBiz.initVersion(typeS,versionS);
                result.setData(vo);
            }
        }catch (Exception e){
            logger.error("AppController init method error",e);
        }
        return result;
    }
}
