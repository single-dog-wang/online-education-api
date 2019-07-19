package com.wodeer.timesheet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wodeer.timesheet.entity.User;
import com.wodeer.timesheet.service.TaskService;
import com.wodeer.timesheet.service.UserService;
import com.wodeer.timesheet.viewobject.PageVo;
import com.wodeer.timesheet.viewobject.TaskVo;
import com.wodeer.timesheet.viewobject.UserVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guoya
 */
@RestController
public class ExportExcelController {

    @Autowired
    HttpServletResponse response;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    /**
     * 使用POI编程导出User表格,导入到客户端的浏览器上
     * @throws Exception 异常
     */
    @GetMapping("/exportUser")
    public void exportUser() throws Exception {
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("timesheet项目--用户表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("用户概览表");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("用户编号");
        row2.createCell(1).setCellValue("用户名");
        row2.createCell(2).setCellValue("领导id");
        row2.createCell(3).setCellValue("用户类型");
        row2.createCell(4).setCellValue("是否启用");
        row2.createCell(5).setCellValue("创建时间");
        //调用UserService中的方法
        IPage<User> userPage = userService.queryByPage(1, 100, 1);
        PageVo<UserVo> result = new PageVo<>();
        result.setRecords(userPage.getRecords().stream().map(UserVo::new).collect(Collectors.toList()));
        List<UserVo> userList = result.getRecords();
        //List转换成Array
        UserVo[] userVos = userList.toArray(new UserVo[0]);
        //遍历数组，给Excel表的每一行赋值（从第二行开始）
        for(int i = 0; i < userVos.length; i++ ){
            int j = i+2;
            HSSFRow row=sheet.createRow(j);
            row.createCell(0).setCellValue(userVos[i].getId());
            row.createCell(1).setCellValue(userVos[i].getUsername());
            row.createCell(2).setCellValue(userVos[i].getLeaderId());
            row.createCell(3).setCellValue(userVos[i].getUserType());
            row.createCell(4).setCellValue(userVos[i].getIsActive());
            // 日期格式转为字符串输出（针对日期类型的数据）
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time= sdf.format(userVos[i].getCreateTime());
            row.createCell(5).setCellValue(time);
        }
        //输出Excel文件
        response.setHeader("Content-disposition", "attachment; filename=timesheet.xls");
        response.setContentType("application/msexcel");
        OutputStream output=response.getOutputStream();
        wb.write(output);
        output.close();
    }

    /**
     * 使用POI编程导出Task表格,导入到客户端的浏览器上
     * @throws Exception 异常
     */
    @GetMapping("/exportTask")
    public void exportTask() throws Exception {
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("timesheet项目--工作内容占比表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("工作内容占比表");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("工作内容");
        row2.createCell(7).setCellValue("工作类型");
        row2.createCell(8).setCellValue("占比");
        //调用方法得到工作内容列表
        List<TaskVo> content = taskService.queryContent(1);
        //调用方法得到工作内容的分母
        Long deno = taskService.queryDeno(1);
        //调用方法得到工作内容的分子列表
        List<Long> mole = taskService.queryMole(1);
        //List转换成Array
        TaskVo[] taskVos = content.toArray(new TaskVo[0]);
        Long[] moles = mole.toArray(new Long[0]);
        float sum = 0;
        //遍历数组，给Excel表的每一行赋值（从第二行开始）
        for(int i = 0; i < taskVos.length; i++ ){
            int j = i+2;
            HSSFRow row=sheet.createRow(j);
            row.createCell(0).setCellValue(taskVos[i].getWorkContent());
            row.createCell(7).setCellValue(taskVos[i].getWorkType());
            if(i < taskVos.length-1){
                Long mole2 = moles[i];
                float percent = BigDecimal.valueOf(mole2).divide(BigDecimal.valueOf(deno), 4, BigDecimal.ROUND_HALF_UP).floatValue();
                System.out.println(percent);
                row.createCell(8).setCellValue(percent);
                sum += percent;
            }else{
                BigDecimal b = new BigDecimal(sum);
                float newSum = b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
                float percent = 1 - newSum;
                row.createCell(8).setCellValue(percent);
            }
        }
        //输出Excel文件
        response.setHeader("Content-disposition", "attachment; filename=timesheet.xls");
        response.setContentType("application/msexcel");
        OutputStream output=response.getOutputStream();
        wb.write(output);
        output.close();
    }
}
