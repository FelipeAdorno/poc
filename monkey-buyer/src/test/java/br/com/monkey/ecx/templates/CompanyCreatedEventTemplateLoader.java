package br.com.monkey.ecx.templates;

import br.com.monkey.ecx.producer.CompanyCreatedEvent;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.monkey.ecx.templates.FixtureTemplates.VALID;

public class CompanyCreatedEventTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(CompanyCreatedEvent.class).addTemplate(VALID.name(), new Rule() {{
            add("role", random("ADMIN", "EXECUTE", "SELECTOR", "VIEW"));
            add("status", "ACTIVE");
            add("createUserIp", regex("(\\d{3}).(\\d{3}).(\\d{3}).(\\d{3})"));
            add("companyId", random(Integer.class, 1, 800));
            add("userId", random(Integer.class, 1, 800));
        }});
    }
}