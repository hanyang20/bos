package com.itheima.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.itheima.bos.web.common.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import net.sf.json.JSONObject;

/**
 * ClassName:ImageAction <br/>
 * Function: <br/>
 * Date: Nov 18, 2017 4:42:41 PM <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends BaseAction<Object> {


    // 使用属性驱动获取上传的文件
    private File imgFile;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    // 文件名
    private String imgFileFileName;
    // 类型
    private String imgFileContentType;

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    // 上传文件
    @Action("imageAction_upload")
    public String upload() throws IOException {

        Map<String, Object> map = new HashMap<>();
        try {
            // 绝对磁盘路径
            // /bos_management_web/upload
            String saveDirPath = "upload";
            // 保存文件的文件夹的绝对磁盘路径
            String saveDirRealPath = ServletActionContext.getServletContext()
                    .getRealPath(saveDirPath);

            // 截取后缀
            String suffix =
                    imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            // 文件名
            String fileName =
                    UUID.randomUUID().toString().replaceAll("-", "") + suffix;

            // 保存文件
            FileUtils.copyFile(imgFile, new File(saveDirRealPath, fileName));

            map.put("error", 0);
            // http://localhost:8080/bos_management_web/upload/a.jpg
            // 相对路径 /bos_management_web/upload/a.jpg

            // 本项目的路径/bos_management_web
            String contextPath =
                    ServletActionContext.getServletContext().getContextPath();
            map.put("url", contextPath + "/upload/" + fileName);
        } catch (IOException e) {

            e.printStackTrace();
            map.put("error", 1);
            map.put("message", e.getMessage());
        }

        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }

    // 处理图片空间请求
    @Action("imageAction_manager")
    public String manager() throws IOException {
        // 图片扩展名
        String[] fileTypes = new String[] {"gif", "jpg", "jpeg", "png", "bmp"};
        // 绝对磁盘路径
        // /bos_management_web/upload
        String saveDirPath = "upload";
        // 保存文件的文件夹的绝对磁盘路径
        String saveDirRealPath = ServletActionContext.getServletContext()
                .getRealPath(saveDirPath);
        // 保存上传的图片的文件夹
        File currentPathFile = new File(saveDirRealPath);

        // 遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash =
                        new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt =
                            fileName.substring(fileName.lastIndexOf(".") + 1)
                                    .toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo",
                            Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(file.lastModified()));
                fileList.add(hash);
            }
        }
        // 获取本项目的路径
        String contextPath =
                ServletActionContext.getServletContext().getContextPath();

        Map<String, Object> map = new HashMap<>();
        map.put("file_list", fileList);
        map.put("current_url", contextPath+"/upload/");

        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
}
