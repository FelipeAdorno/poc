package br.com.monkey.ecx.templates;

import br.com.monkey.ecx.buyer.enumeration.BuyerLegalResponsibleStatusEnum;
import br.com.monkey.ecx.buyer.domain.BuyerLegalResponsible;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.monkey.ecx.templates.FixtureTemplates.VALID;

public class BuyerLegalResponsibleTemplateLoader implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(BuyerLegalResponsible.class).addTemplate(VALID.name(), new Rule() {{
            add("id", random(Integer.class, range(1, 850)));
            add("name", random("Felipe Adorno", "Carlos Alberto", "Claudio Rosa"));
            add("governmentId1", cnpj());
            add("governmentId2", cnpj());
            add("phone", regex("(\\d{2})-(\\d{4})-(\\d{4})"));
            add("email", random("felipe@gmail.com", "carlos@gmail.com", "claudio@gmail.com"));
            add("status", random(BuyerLegalResponsibleStatusEnum.ACTIVE.name(),
                    BuyerLegalResponsibleStatusEnum.INACTIVE.name()));
        }});
    }
}
