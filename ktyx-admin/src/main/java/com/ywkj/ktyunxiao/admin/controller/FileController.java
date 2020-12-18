package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.ExcelUtil;
import com.ywkj.ktyunxiao.common.utils.FileUtil;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelCustomerPojo;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelGoodsPojo;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelStaffPojo;
import com.ywkj.ktyunxiao.service.CustomerService;
import com.ywkj.ktyunxiao.service.GoodsService;
import com.ywkj.ktyunxiao.service.StaffService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件
 * @author LiWenHao
 * @date 2018/4/11 21:39
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CustomerService customerService;

    /**
     * 下载
     * @param fileName  文件名
     * @param response
     */
    @GetMapping(value = "/downFile/{fileName:.+}")
    public void uploadExcel(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        FileUtil.fileDown(response,fileName);
    }

    /**
     * 导出excel
     * @param type      类型
     * @param response
     */
    @GetMapping(value = "/export/{type}")
    public void exportExcel(@PathVariable("type") String type, @SessionAttribute("staff") Staff staff, HttpServletResponse response) {
        Workbook workbook = null;
        switch (type){
            case "staff":
                workbook = ExcelUtil.exportExcel(staffService.exportStaff(staff), ExcelStaffPojo.class);
                break;
            case "customer":
                workbook = ExcelUtil.exportExcel(customerService.exportCustomer(staff), ExcelCustomerPojo.class);
                break;
            case "goods":
                workbook = ExcelUtil.exportExcel(goodsService.exportGoods(staff.getCompanyId()), ExcelGoodsPojo.class);
                break;
            default:
                throw new ParamException();
        }
        //设置下载时客户端Excel的名称
        String filename = System.currentTimeMillis() + ".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream os;
        try {
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
