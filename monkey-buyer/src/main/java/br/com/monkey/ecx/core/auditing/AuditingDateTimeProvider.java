package br.com.monkey.ecx.core.auditing;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AuditingDateTimeProvider implements DateTimeProvider {

    private final Clock systemClock;
    
    public AuditingDateTimeProvider(Clock systemClock) {
        this.systemClock = systemClock;
    }
    
    @Override
    public Calendar getNow() {
        return GregorianCalendar.from(ZonedDateTime.now(systemClock));
    }
}