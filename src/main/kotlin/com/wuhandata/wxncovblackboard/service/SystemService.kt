package com.wuhandata.wxncovblackboard.service

import com.wuhandata.wxncovblackboard.common.utils.Helper
import com.wuhandata.wxncovblackboard.common.vo.DataTable
import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.common.vo.TreeNode
import com.wuhandata.wxncovblackboard.domain.system.Dict
import com.wuhandata.wxncovblackboard.domain.system.Permission
import com.wuhandata.wxncovblackboard.domain.system.Role
import com.wuhandata.wxncovblackboard.domain.system.User
import com.wuhandata.wxncovblackboard.repo.system.DictRepository
import com.wuhandata.wxncovblackboard.repo.system.PermissionRepository
import com.wuhandata.wxncovblackboard.repo.system.RoleRepository
import com.wuhandata.wxncovblackboard.repo.system.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@Service
class SystemService {

    @Autowired
    lateinit var userRepo: UserRepository

    @Autowired
    lateinit var permissionRepo: PermissionRepository

    @Autowired
    lateinit var roleRepo: RoleRepository

    @Autowired
    lateinit var dictRepo: DictRepository

    fun saveDict(dict: Dict) {
        if(dict.id==0L&&dictRepo.findByMask(dict.mask)!=null) throw Exception("标识已存在!")
        if(dict.parentId>0){
            val parent = dictRepo.getOne(dict.parentId)
            dict.parentMask = parent.mask
        }
        dictRepo.save(dict)
    }

    fun deleteDicts(strs: String){
        val ids = strs.split(",")
        for(id in ids)
            dictRepo.deleteById(id.toLong())
    }

    fun getDict(id: Long): Dict{
        return dictRepo.getOne(id)
    }

    fun getShopTypeById(id: Long): String {
        return if (id > 0)
            dictRepo.getOne(id).name
        else
            ""
    }

    fun getDictNameByMask(mask: String): String{
        val dict = dictRepo.findByMask(mask)
        return dict?.name ?: ""
    }

    fun getDicts(pid: Long, params: DataTableParams): DataTable<Dict> {
        val list = dictRepo.findAllByParentId(pid)
        val dt = DataTable<Dict>()
        dt.data = list
        dt.recordsFiltered = list.size.toLong()
        dt.recordsTotal = list.size.toLong()
        return dt
    }

    fun getDictTreesByParentId(id: Long): List<TreeNode> {
        val list = dictRepo.findAllByParentId(id)
        val result: MutableList<TreeNode> = ArrayList()
        for(dict in list){
            val node = TreeNode()
            node.id = dict.id.toString()
            node.text = dict.name + "["+dict.mask+"]"
            if (dictRepo.findAllByParentId(dict.id).isNotEmpty())
                node.children = true
            result.add(node)
        }
        return result
    }

    fun getDictsByParentMask(mask: String): List<Dict>{
        return dictRepo.findAllByParentMask(mask)
    }

    fun savePermission(permission: Permission){
        if(permission.id==0L&&permissionRepo.findByMask(permission.mask)!=null) throw Exception("标识已存在!")
        permissionRepo.save(permission)
    }

    fun deletePermissions(strs: String){
        val ids = strs.split(",")
        for(id in ids)
            permissionRepo.deleteById(id.toLong())
    }

    fun getPermission(id: Long): Permission{
        return permissionRepo.getOne(id)
    }

    fun getAllPermissions(): MutableList<Permission>{
        return permissionRepo.findAll()
    }

    fun getPermissions(pid: Long, type: String): DataTable<Permission>{
        val list = permissionRepo.findAllByTypeAndPid(type, pid)
        val dt = DataTable<Permission>()
        dt.data = list
        dt.recordsFiltered = list.size.toLong()
        dt.recordsTotal = list.size.toLong()
        return dt
    }

    fun getPermissionModuleTreesByParentId(id: Long): List<TreeNode>{
        val list = permissionRepo.findAllByTypeAndPid("PERMISSION_TYPE_MODULE", id)
        val result: MutableList<TreeNode> = ArrayList()
        for(perm in list){
            val node = TreeNode()
            node.id = perm.id.toString()
            node.text = perm.name + "["+perm.mask+"]"
            if (permissionRepo.findAllByTypeAndPid("PERMISSION_TYPE_MODULE", perm.id).isNotEmpty())
                node.children = true
            result.add(node)
        }
        return result
    }

    fun getAllPermissionsWithRoleSelected(role: Role): MutableList<Permission>{
        val permissions = permissionRepo.findAllByTypeAndPid("PERMISSION_TYPE_MODULE", 0)
        for (permission in permissions) {
            for (p in role.permissions) {
                if (p.id==permission.id)
                    permission.hasPermission = true
            }
            val children = permissionRepo.findAllByTypeAndPid("PERMISSION_TYPE_MODULE", permission.id)
            for(child in children){
                for(m in role.permissions){
                    if(m.id == child.id)
                        child.hasPermission = true
                }
                permission.children.add(child)
            }
        }
        return permissions
    }

    fun assignPermissionsToRole(roleId: Long, permission: String){
        val role = getRole(roleId)
        role.permissions.clear()
        if(permission!="") {
            val permissions = permission.split(",")
            for (id in permissions) {
                val perm = getPermission(id.toLong())
                role.permissions.add(perm)
            }
        }
        saveRole(role)
    }

    fun getModulesByUsername(username: String): MutableList<Permission>{
        val list = permissionRepo.findModulesByUsername(username, 0)
        for(perm in list){
            val children = permissionRepo.findModulesByUsername(username, perm.id)
            for(child in children)
                perm.children.add(child)
        }
        return list
    }

    fun saveRole(role: Role){
        if(role.id==0L&&roleRepo.findByMask(role.mask)!=null) throw Exception("标识已存在!")
        roleRepo.save(role)
    }

    fun deleteRoles(strs: String){
        val ids = strs.split(",")
        for(id in ids)
            roleRepo.deleteById(id.toLong())
    }

    fun getRole(id: Long): Role{
        return roleRepo.getOne(id)
    }

    fun getRoles(params: DataTableParams): DataTable<Role> {
        val pageable = Helper.makePage(params)
        val list = roleRepo.findAll(pageable)
        val dt = DataTable<Role>()
        dt.data = list.content
        dt.recordsFiltered = list.totalElements
        dt.recordsTotal = list.totalElements
        return dt
    }

    fun getAllRolesWithUserSelected(user: User): MutableList<Role>{
        val roles = roleRepo.findAll()
        for (role in roles) {
            for (r in user.roles) {
                if (r.id == role.id)
                    role.hasRole = true
            }
        }
        return roles
    }

    fun saveUser(user: User){
        if(user.id==0L&&userRepo.findByUsername(user.username)!=null) throw Exception("账号已存在!")
        if(user.id==0L)
            user.password = BCrypt.hashpw(user.password, BCrypt.gensalt())
        userRepo.save(user)
    }

    fun deleteUsers(strs: String){
        val ids = strs.split(",")
        for(id in ids)
            userRepo.deleteById(id.toLong())
    }

    fun getUser(id: Long): User{
        return userRepo.getOne(id)
    }

    fun getUsers(username: String?, type: String?, clientId: Long,  params: DataTableParams): DataTable<User>{
        val pageable = Helper.makePage(params)
        val list = userRepo.findAll({ root: Root<User>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
            var predicate: Predicate? = null
            if (!StringUtils.isEmpty(username)) {
                predicate = criteriaBuilder.like(root.get<String>("username"), "%$username%")
            }
            if (!StringUtils.isEmpty(type)) {
                predicate = criteriaBuilder.equal(root.get<String>("type"), type)
            }
            if (clientId > 0) {
                predicate = criteriaBuilder.equal(root.get<Long>("clientId"), clientId)
            }
            predicate
        }, pageable)
        val dt = DataTable<User>()
        dt.data = list.content
        dt.recordsFiltered = list.totalElements
        dt.recordsTotal = list.totalElements
        return dt
    }

    fun getUserWithPermissions(username: String): User{
        val user = userRepo.findByUsername(username)
        user!!.permissions = permissionRepo.findAllByUsername(username)
        return user
    }

    fun assignRolesToUser(userId: Long, roles: String){
        val user = getUser(userId)
        user.roles.clear()
        if(roles!="") {
        val role = roles.split(",")
            for (rid in role) {
                val r = getRole(rid.toLong())
                user.roles.add(r)
            }
        }
        saveUser(user)
    }

}
