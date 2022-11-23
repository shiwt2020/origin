package cn.esthe.exportData;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xusj
 * @date 2021-05-13- 19:40 19:40
 */
public class ExcelData {

    private XSSFSheet sheet;

    /**
     * 构造函数，初始化excel数据
     *
     * @param filePath  excel路径
     * @param sheetName sheet表名
     */
    ExcelData(String filePath, String sheetName) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            sheet = sheets.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <店铺id, erp的店名>
    // 数据来源 sbc.customer.store_erpstore_rel
    private static Map<String, String> storeMap = new HashMap<>();

    static {
        storeMap.put("123458067", "联创好生活商城");
        storeMap.put("123458032", "市民好生活");
        storeMap.put("123458566", "苏颐养1号店");
        storeMap.put("123458555", "企福惠");
        storeMap.put("123458247", "市民好生活");
        storeMap.put("123458565", "市民好生活惠民社区南京店");
        storeMap.put("123459086", "市民好生活惠民社区苏州店");
        storeMap.put("123459088", "市民好生活惠民社区常州店");
        storeMap.put("123459087", "市民好生活惠民社区淮安店");
        storeMap.put("123459089", "市民好生活惠民社区徐州店");
        storeMap.put("123459085", "市民好生活惠民社区枣庄店");
        storeMap.put("123458992", "市民好生活·苏州市企业内购");
        storeMap.put("123458046", "联创好生活商城");
        storeMap.put("123468183", "市民好生活");
    }


    public static void main(String[] args) {
        // 导出文件的地址
        File file = new File("C:\\Users\\Utopia\\Desktop\\abc\\trade.xlsx");
        // 备注
        String remark = "手动补偿";

        ExcelReader reader = ExcelUtil.getReader(file);
        List<List<Object>> list = reader.read();

        List<TestBean> rows = new ArrayList<>();

        list.forEach(item -> {
            TestBean bean = new TestBean();
            // 第一列订单号
            String orderNo = item.get(0).toString();
            bean.set业务订单号(orderNo);
            // 买家信息 buyer
            String buyerString = item.get(1).toString();
            JSONObject buyerJson = JSON.parseObject(buyerString);
            // 店铺信息 supplier
            String supplierString = item.get(2).toString();
            JSONObject supplierJson = JSON.parseObject(supplierString);
            String storeId = JSON.parseObject(
                    supplierJson.getString("storeId")
            ).getString("$numberLong");
            // 支付信息 tradeState
            String tradeString = item.get(3).toString();
            JSONObject tradeJson = JSON.parseObject(tradeString);
            // 地址信息 consignee
            String consigneeString = item.get(4).toString();
            JSONObject consigneeJson = JSON.parseObject(consigneeString);
            // 金额信息 tradePrice
            String priceString = item.get(5).toString();
            JSONObject priceJson = JSON.parseObject(priceString);
            // 商品信息 tradeItems
            String itemsString = item.get(6).toString();
            JSONArray itemsJson = JSON.parseArray(itemsString);
            String createTime = JSON.parseObject(
                    JSON.parseObject(
                            tradeJson.getString("createTime")
                    ).getString("$date")
            ).getString("$numberLong");

            bean.set订单时间(ExcelData.stampToDate(createTime));
            bean.set买家姓名(buyerJson.getString("name"));
            bean.set付款订单号("");
            bean.set付款状态("");
            bean.set付款时间("");
            bean.set卖家备注("");
            bean.set买家备注("");
            bean.set运费(priceJson.getString("deliveryPrice"));
            bean.set应付金额(priceJson.getString("totalPrice"));
            bean.set姓名(consigneeJson.getString("name"));
            bean.set电话(consigneeJson.getString("phone"));
            bean.set座机("");
            bean.set省("江苏省");
            bean.set市("南京市");
            bean.set区("鼓楼区");
            bean.set详细地址(Objects.toString(consigneeJson.getString("detailAddress"), "社区自提"));
            bean.set邮编("");
            bean.set在线sku("");
            bean.setSku("");
            bean.set商品名称("");
            bean.setSku类型("");
            bean.set数量("");
            bean.set售价("");
            bean.set快递("");
            bean.set单号("");
            bean.set发货时间("");
            bean.set状态("已付款待审核");
            bean.set业务员("");
            bean.set便签(remark);
            bean.set店铺名称(storeMap.get(storeId));

            itemsJson.forEach(goodInfo -> {
                TestBean beanNew = new TestBean();
                BeanUtil.copyProperties(bean, beanNew);
                JSONObject jsonObject = JSON.parseObject(goodInfo.toString());
                beanNew.setSku(jsonObject.getString("thirdPlatformSkuId"));
                String num = JSON.parseObject(
                        jsonObject.getString("num")
                ).getString("$numberLong");
                beanNew.set数量(num);
                beanNew.set售价(jsonObject.getString("originalPrice"));
                beanNew.set商品名称(jsonObject.getString("skuName"));
                rows.add(beanNew);
            });
        });

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("C:\\Users\\Utopia\\Desktop\\abc\\trade_pre.xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        //  writer.merge(4, "sheet");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 根据行和列的索引获取单元格的数据
     *
     * @param row
     * @param column
     * @return
     */
    public String getExcelDateByIndex(int row, int column) {
        XSSFRow row1 = sheet.getRow(row);
        String cell = row1.getCell(column).toString();
        return cell;
    }

    /**
     * 根据某一列值为“******”的这一行，来获取该行第x列的值
     *
     * @param caseName
     * @param currentColumn 当前单元格列的索引
     * @param targetColumn  目标单元格列的索引
     * @return
     */
    public String getCellByCaseName(String caseName, int currentColumn, int targetColumn) {
        String operateSteps = "";
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            String cell = row.getCell(currentColumn).toString();
            if (cell.equals(caseName)) {
                operateSteps = row.getCell(targetColumn).toString();
                break;
            }
        }
        return operateSteps;
    }

    //打印excel数据
    public void readExcelData() {
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for (int j = 0; j < columns; j++) {
                String cell = row.getCell(j).toString();
                System.out.println(cell);
            }
        }
    }
}
