package br.com.monkey.ecx.core;

import org.springframework.data.domain.Page;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MonkeyPagedResourceAssembler<T> extends PagedResourcesAssembler<T> {


    public MonkeyPagedResourceAssembler(HateoasPageableHandlerMethodArgumentResolver resolver) {
        super(resolver, null);
    }

    @SuppressWarnings("unchecked")
    public <R extends ResourceSupport> PagedResources<R> toResource(Class<?> domainClass,
                                                                    Page<T> page, ResourceAssembler<T, R> assembler) {
        if(CollectionUtils.isEmpty(page.getContent())) {
            return (PagedResources<R>) super.toEmptyResource(page, domainClass, null);
        }
        return super.toResource(page, assembler);
    }

    @Override
    public PagedResources<?> toEmptyResource(Page<?> page, Class<?> type, Link link) {
        return super.toEmptyResource(page, type, link);
    }
}
