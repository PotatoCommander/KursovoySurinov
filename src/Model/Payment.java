package Model;

import java.util.Date;

public class Payment
{
    public Date DateOfPayment;

    public double TotalSum;
    public double ColdWaterSum;
    public double HotWaterSum;

    public double ColdWaterCost;
    public double HotWaterCost;

    public double HotWaterPrevMonthVolume;
    public double ColdWaterPrevMonthVolume;

    public double HotWaterCurrMonthVolume;
    public double ColdWaterCurrMonthVolume;

    public int MonthFrom;
    public int MonthTo;
    public Payment()
    {
        DateOfPayment = new Date(System.currentTimeMillis());
    }
}
