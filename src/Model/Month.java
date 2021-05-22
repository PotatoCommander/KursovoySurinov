package Model;

public enum Month
{
    JANUARY("Январь"),
    FEBRUARY("Февраль"),
    MARCH("Март"),
    APRIL("Апрель"),
    MAY("Май"),
    JUNE("Июнь"),
    JULE("Июль"),
    AUGUST("Август"),
    SEPTEMBER("Сентябрь"),
    OCTOBER("Октябрь"),
    NOVEMBER("Ноябрь"),
    DECEMBER("Декабрь");

    private final String text;
    Month(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }

}