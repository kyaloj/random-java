package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestApp {

    public static void main(String[] args) {
        ITransferApp transferApp = new TransferApp();
        System.out.println(transferApp.getTransfers(new Config(LocalDate.now(), new BigDecimal(10), Frequency.MONTHLY, 3)));
        System.out.println(transferApp.getTransfers(new Config(LocalDate.now(), new BigDecimal(10), Frequency.WEEKLY, 3)));
        System.out.println(transferApp.getTransfers(new Config(LocalDate.now(), new BigDecimal(10), Frequency.DAILY, 3)));
    }
}

enum Frequency {
    WEEKLY,
    MONTHLY,
    DAILY
}

record Config(
        LocalDate date,
        BigDecimal amount,
        Frequency frequency,
        int count
){}

record Transfer(
        BigDecimal amount,
        LocalDate date
) {}

interface ITransferApp {
    List<Transfer> getTransfers(Config config);
}

interface Schedule {
    LocalDate generateDate(LocalDate startDate, int interval);
}

class MonthlySchedule implements Schedule {

    @Override
    public LocalDate generateDate(LocalDate date, int interval) {
        return date.plusMonths(interval);
    }
}

class WeeklySchedule implements Schedule {

    @Override
    public LocalDate generateDate(LocalDate date, int interval) {
        return date.plusWeeks(interval);
    }
}

class DailySchedule implements Schedule {

    @Override
    public LocalDate generateDate(LocalDate date, int interval) {
        return date.plusWeeks(interval);
    }
}

interface IScheduleFactory {
    Schedule generateSchedule(Config config);
}

class ScheduleFactory implements IScheduleFactory {

    @Override
    public Schedule generateSchedule(Config config) {
        Schedule schedule =  switch (config.frequency()) {
            case WEEKLY -> new WeeklySchedule();
            case MONTHLY -> new MonthlySchedule();
            case DAILY -> new DailySchedule();
        };
        return schedule;
    }
}

class TransferApp implements ITransferApp {
    IScheduleFactory scheduleFactory = new ScheduleFactory();

    @Override
    public List<Transfer> getTransfers(Config config) {
        Schedule schedule = scheduleFactory.generateSchedule(config);
        List<Transfer> transfers = new ArrayList<>();

        for (int i = 0; i < config.count(); i ++) {
            transfers.add(new Transfer(config.amount(), schedule.generateDate(config.date(), i)));
        }
        return transfers;
    }
}
