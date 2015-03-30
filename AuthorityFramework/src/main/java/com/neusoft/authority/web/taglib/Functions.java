package com.neusoft.authority.web.taglib;

import com.neusoft.authority.entity.Organization;
import com.neusoft.authority.entity.Resource;
import com.neusoft.authority.entity.Role;
import com.neusoft.authority.service.OrganizationService;
import com.neusoft.authority.service.ResourceService;
import com.neusoft.authority.service.RoleService;
import com.neusoft.authority.utils.SpringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Created by admin on 2015/3/26.
 */
public class Functions {
    private static OrganizationService organizationService;
    private static RoleService roleService;
    private static ResourceService resourceService;

    public static OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = SpringUtils.getBean(OrganizationService.class);
        }
        return organizationService;
    }

    public static RoleService getRoleService() {
        if (roleService == null) {
            roleService = SpringUtils.getBean(RoleService.class);
        }
        return roleService;
    }

    public static ResourceService getResourceService() {
        if (resourceService == null) {
            resourceService = SpringUtils.getBean(ResourceService.class);
        }
        return resourceService;
    }

    public static boolean in(Iterable iterable, Object element) {
        if (iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }

    public static String organizationName(Long organizationId) {
        Organization organization = getOrganizationService().findOne(organizationId);
        if (organization == null) {
            return "";
        }
        return organization.getName();
    }

    public static String organizationNames(Collection<Long> organizationIds) {
        if (CollectionUtils.isEmpty(organizationIds)) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (Long organizationId : organizationIds) {
            Organization organization = getOrganizationService().findOne(organizationId);
            if (organization == null) {
                return "";
            }
            sb.append(organization.getName() + ",");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String roleName(Long roleId) {
        Role role = getRoleService().findOne(roleId);
        if (role == null) {
            return "";
        }
        return role.getDescription();
    }

    public static String roleNames(Collection<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Long roleId : roleIds) {
            Role role = getRoleService().findOne(roleId);
            if (role == null) {
                return "";
            }
            sb.append(role.getDescription() + ",");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String resourceName(Long resourceId) {
        Resource resource = getResourceService().findOne(resourceId);
        if (resource == null) {
            return "";
        }
        return resource.getName();
    }

    public static String resourceNames(Collection<Long> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Long resourceId : resourceIds) {
            Resource resource = getResourceService().findOne(resourceId);
            if (resource == null) {
                return "";
            }
            sb.append(resource.getName() + ",");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static boolean descendantsOfGivenResources(Collection<Long> resourceIds, Long aResourceId) {
        boolean ret = false;
        Resource aResource = getResourceService().findOne(aResourceId);
        for (Long resourceId : resourceIds) {
            Resource resource = getResourceService().findOne(resourceId);

            if (aResource.getParentIds().startsWith(resource.getParentIds()) && aResource.getParentIds().length() > resource.getParentIds().length() ) {
                ret = true;
                break;
            }
        }
        return ret;
    }
}
