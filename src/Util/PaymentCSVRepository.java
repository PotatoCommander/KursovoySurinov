package Util;

import Model.CSVModel;
import Model.Month;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PaymentCSVRepository
{
    private String filePath;
    SimpleDateFormat sdf;
    public void setFilePath(String path)
    {
        this.filePath = path;
    }
    private String[] header = {"Month", "Cold", "Hot","ColdPrice", "HotPrice", "HotWaterSum", "ColdWaterSum", "Sum"};

    public PaymentCSVRepository()
    {
        filePath = null;
    }

    public ArrayList<CSVModel> GetAll()
    {
        var records = new ArrayList<CSVModel>();
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader(header)
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            for (CSVRecord csvRecord : csvParser)
            {
                var month = Integer.valueOf(csvRecord.get("Month"));
                var cold = Double.parseDouble(csvRecord.get("Cold"));
                var hot = Double.parseDouble(csvRecord.get("Hot"));
                var coldPrice = Double.parseDouble(csvRecord.get("ColdPrice"));
                var hotPrice = Double.parseDouble(csvRecord.get("HotPrice"));
                var hotWaterSum = Double.parseDouble(csvRecord.get("HotWaterSum"));
                var coldWaterSum = Double.parseDouble(csvRecord.get("ColdWaterSum"));
                var Sum = Double.parseDouble(csvRecord.get("Sum"));
                var payment = new CSVModel();

                payment.id = month;
                payment.coldWater = cold;
                payment.hotWater = hot;
                payment.hotWaterPrice = hotPrice;
                payment.coldWaterPrice = coldPrice;
                payment.hotWaterSum = hotWaterSum;
                payment.coldWaterSum = coldWaterSum;
                payment.sum = Sum;

                records.add(payment);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return records;
    }
    public boolean Save(ArrayList<CSVModel> items)
    {
        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));
            for (CSVModel item : items)
            {
                csvPrinter.printRecord(item.id, item.coldWater, item.hotWater,
                        item.coldWaterPrice, item.hotWaterPrice, item.hotWaterSum, item.coldWaterSum,
                        item.sum);
            }
            csvPrinter.flush();
            return true;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean SaveAs(ArrayList<CSVModel> items)
    {
        File file = new File(filePath);
        if (!isFileExist(file))
        {
            if(!CreateFile())
            {
                return false;
            }
        }
        return Save(items);
    }
    public boolean CreateFile()
    {
        try
        {
            File file = new File(filePath);
            var result = file.createNewFile();
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    private boolean isFileExist(File file)
    {
        return file.exists() && !file.isDirectory();
    }
}
