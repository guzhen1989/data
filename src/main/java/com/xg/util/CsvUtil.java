package com.xg.util;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * csv tool
 *
 * @author guzhen
 * @date 2018/7/12
 */
@Slf4j
public class CsvUtil {
  private static final String NEW_LINE_SEPARATOR = System.getProperty("line.separator");

  /**
   * 读取csv文件
   *
   * @param bytes 文件内容
   * @param headers csv列头
   * @return CSVRecord 列表
   */
  public static List<CSVRecord> readCSV(byte[] bytes,Charset charset, String... headers) {

    // 创建CSVFormat
    CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
    try (InputStreamReader inputStreamReader =
            new InputStreamReader(new ByteArrayInputStream(bytes),charset);
        CSVParser parser = new CSVParser(inputStreamReader, formator); ) {
      return parser.getRecords();
    } catch (IOException e) {
      throw new RuntimeException("CSV文件读取失败", e);
    }
  }

  /**
   * 写入csv文件
   *
   * @param headers 列头
   * @param data 数据内容
   * @param filePath 创建的csv文件路径
   */
  public static void writeCsv(String[] headers, List<String[]> data, String filePath) {
    try {
      // 初始化csvformat
      CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

      // 创建FileWriter对象
      FileWriter fileWriter = new FileWriter(filePath);

      // 创建CSVPrinter对象
      CSVPrinter printer = new CSVPrinter(fileWriter, formator);

      // 写入列头数据
      printer.printRecord((Object[]) headers);

      if (null != data) {
        // 循环写入数据
        for (String[] lineData : data) {
          printer.printRecord((Object[]) lineData);
        }
      }
      log.info("CSV文件创建成功,文件路径:" + filePath);
    } catch (IOException e) {
      log.warn("CSV文件创建失败,文件路径:" + filePath);
      throw new RuntimeException("CSV文件创建失败", e);
    }
  }
}
