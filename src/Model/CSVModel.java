package Model;

import java.util.Objects;

public class CSVModel
{
    public int id;
    public double coldWater;
    public double hotWater;
    public double hotWaterPrice;
    public double coldWaterPrice;
    public double hotWaterSum;
    public double coldWaterSum;
    public double sum;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CSVModel csvModel = (CSVModel) o;
        return id == csvModel.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
