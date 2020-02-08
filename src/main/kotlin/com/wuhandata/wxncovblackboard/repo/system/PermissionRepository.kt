package com.wuhandata.wxncovblackboard.repo.system

import com.wuhandata.wxncovblackboard.domain.system.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PermissionRepository: JpaRepository<Permission, Long>{

    @Query("select DISTINCT(p.id), p.* from SYS_PERMISSION p inner join SYS_PERMISSION_ROLE pr on pr.PERMISSION_ID = p.ID inner join SYS_ROLE_USER ru on pr.ROLE_ID = ru.ROLE_ID inner join SYS_USER su on ru.USER_ID = su.ID where p.TYPE='PERMISSION_TYPE_MODULE' AND su.USERNAME=?1 AND p.PARENT_ID=?2 order by seq ASC", nativeQuery = true)
    fun findModulesByUsername(username: String, pid: Long): MutableList<Permission>

    @Query("select DISTINCT(p.id), p.* from SYS_PERMISSION p inner join SYS_PERMISSION_ROLE pr on pr.PERMISSION_ID = p.ID inner join SYS_ROLE_USER ru on pr.ROLE_ID = ru.ROLE_ID inner join SYS_USER su on ru.USER_ID = su.ID where su.USERNAME=?1 order by seq ASC", nativeQuery = true)
    fun findAllByUsername(username: String): MutableList<Permission>

    fun findByMask(mask: String): Permission?

    @Query("select * from SYS_PERMISSION WHERE TYPE=?1 AND PARENT_ID=?2 order by seq ASC", nativeQuery = true)
    fun findAllByTypeAndPid(type: String, pid: Long): MutableList<Permission>
}
