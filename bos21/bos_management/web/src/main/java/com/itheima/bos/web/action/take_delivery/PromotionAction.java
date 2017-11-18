package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;
import com.itheima.bos.web.common.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {
    @Autowired
    private PromotionService promotionService;
    // 使用属性驱动获取文件和文件名
    private File titleImgFile;
    private String titleImgFileFileName;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    // 保存操作
    @Action(value = "promotionAction_save",
            results = {@Result(name = "success",
                    location = "/pages/take_delivery/promotion.html",
                    type = "redirect")})
    public String save() {

        Promotion promotion = getModel();

        try {
            String saveDirPath = "upload";
            // 文件夹的绝对路径
            String saveDirRealPath = ServletActionContext.getServletContext()
                    .getRealPath(saveDirPath);
            // 获取文件名的后缀
            String suffix = titleImgFileFileName
                    .substring(titleImgFileFileName.lastIndexOf("."));
            // 生成文件名
            String fileName =
                    UUID.randomUUID().toString().replaceAll("-", "") + suffix;

            FileUtils.copyFile(titleImgFile,
                    new File(saveDirRealPath, fileName));

            // /upload/xx.jpg
            promotion.setTitleImg("/upload/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            promotion.setTitleImg("");
        }

        promotion.setStatus("1");
        promotionService.save(promotion);
        return SUCCESS;
    }


    // 分页查询
    @Action("promotionAction_pageQuery")
    public String pageQuery() throws IOException {

        // PageRequest中的page属性是从0开始的
        // EasyUI传递过来的page属性是从1开始的
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Promotion> page = promotionService.pageQuery(pageable);

        java2Json(page, null);

        return NONE;
    }

}
