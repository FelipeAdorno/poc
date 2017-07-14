package br.com.monkey.ecx.templates;

import br.com.monkey.ecx.buyer.enumeration.BuyerStatusEnum;
import br.com.monkey.ecx.buyer.domain.Buyer;
import br.com.monkey.ecx.buyer.domain.BuyerLegalResponsible;
import br.com.monkey.ecx.core.CustodianBankEnum;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.monkey.ecx.templates.FixtureTemplates.VALID;

public class BuyerTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Buyer.class).addTemplate(VALID.name(), new Rule() {{
            add("id", random(Integer.class, 1, 800545));
            add("companyName", random("Felipe Company", "Adorno Company", "Monkey Company"));
            add("fundGovernmentId", cnpj());
            add("fundName", random("Felipe Found", "Adorno Found", "Monkey Found"));
            add("status", random(BuyerStatusEnum.ACTIVE.name(),
                    BuyerStatusEnum.APPROVAL.name(), BuyerStatusEnum.INACTIVE.name()));
            add("custodianBank", random(CustodianBankEnum.PAULISTA.name(), CustodianBankEnum.SANTANDER.name()));
            add("admName", random("Felipe ADM", "Adorno ADM", "Monkey ADM"));
            add("shortName", random("FEL ", "ADO", "MON"));
            add("admGovernmentId", cnpj());
            add("fundDirectorName", random("Felipe Adorno", "João Carlos", "Claudio Ribeiro"));
            add("fundDirectorCpf", random("12145925465", "15271671941", "84183622514"));
            add("fundDirectorAddress", random("Avenida Paulista", "Avenida Ibirapuera", "Al. Santos"));
            add("fundDirectorAddressNumber", random("123", "127B", "127A"));
            add("fundDirectorAddressComplement", random("Perto do Masp", "Próximo a estação", ""));
            add("fundDirectorNeighborhood", random("Bela Vista", "Ibirapuera", "Moema"));
            add("fundDirectorCity", random("São Paulo", "Rio de Janeiro", "Salvador"));
            add("fundDirectorState", random("SP", "RJ", "ES"));
            add("fundDirectorCountry", "Brasil");
            add("fundDirectorCep", random("06608000", "17720000"));
            add("fundDirectorPhone", regex("(\\d{2})-(\\d{4})-(\\d{4})"));
            add("fundAccountManagerName", random("Felipe Manager", "João Manager", "Monkey Manager"));
            add("fundAccountManagerGovernmentId", cnpj());
            add("directorManagementName", random("Felipe Director", "João Director", "Cluadio Director"));
            add("directorManagementCpf", random("94684396070", "25886866051", "35735628097"));
            add("directorManagementAddress", random("Rua Vergueiro", "Rua da Glória", "Avenida 23 de maio"));
            add("directorManagementAddressNumber", random("500", "500A", "500B"));
            add("directorManagementAddressComplement", random("casa", "apartamento", "Perto do metro"));
            add("directorManagementNeighborhood", random("Centro", "Interlagos", "Saúde"));
            add("directorManagementCity", random("Salvador", "São Paulo", "Vitória"));
            add("directorManagementState", random("Bahia", "São Paulo", "Rio Grande do Sul"));
            add("directorManagementCountry", "Brasil");
            add("fundDirectorCep", random("06608000", "17720000"));
            add("fundDirectorPhone", regex("(\\d{2})-(\\d{4})-(\\d{4})"));
            add("companyLegalResponsibles", has(5).of(BuyerLegalResponsible.class, VALID.name()));
        }});
    }
}