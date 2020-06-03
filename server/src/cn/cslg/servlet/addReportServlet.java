package cn.cslg.servlet;

import cn.cslg.entity.Report;
import cn.cslg.service.IReportService;
import cn.cslg.service.ReportService;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "addReportServlet",urlPatterns = "/addReport")
public class addReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
            fileItemFactory.setSizeThreshold(1024 * 1024 * 2);  //2MB
            String tempDir = this.getServletContext().getRealPath("/temp");
            fileItemFactory.setRepository(new File(tempDir));

            //核心类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            servletFileUpload.setFileSizeMax(1024*1024 * 2);  //2M
            servletFileUpload.setHeaderEncoding("UTF-8");
            String[] params = new String[3];
            List<FileItem> list = servletFileUpload.parseRequest(req);

            //遍历集合获得数据
            for (FileItem fileItem : list) {
                // 判断
                if(fileItem.isFormField()){
                    //是否为表单字段（普通表单元素）
                    //表单字段名称
                    String fieldName = fileItem.getFieldName();
                    //表单字段值 ， 解决普通表单内容的乱码
                    String fieldValue = fileItem.getString("UTF-8");
                    if(fieldName.equals("reportUser")) params[0] = fieldValue;
                    else params[1] = fieldValue;
                } else {
                    String fileName = fileItem.getName();
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    //文件重名
                    fileName = UUID.randomUUID().toString().replace("-", "") + fileName;

                    System.out.println(fileName);
                    //上传内容
                    InputStream is = fileItem.getInputStream();
                    String parentDir = this.getServletContext().getRealPath("/img");
                    File file = new File(parentDir,fileName);
                    //将指定流 写入 到 指定文件中
                    FileUtils.copyInputStreamToFile(is, file);
                    //删除临时文件
                    fileItem.delete();

                    params[2] = fileName;
                }
            }
            IReportService reportService = new ReportService();
            boolean ressult = reportService.addReport(params[0],params[1],params[2]);
            resp.getWriter().print(ressult);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
