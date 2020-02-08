package com.wuhandata.wxncovblackboard.service

import com.wuhandata.wxncovblackboard.common.vo.OptionsVO
import com.wuhandata.wxncovblackboard.domain.system.SpinnerOption
import com.wuhandata.wxncovblackboard.repo.system.FilterItemRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SpinnerOptionService {

    @Autowired
    lateinit var filterItemRepository: FilterItemRepository


    fun save(spinnerOption: SpinnerOption){
        filterItemRepository.save(spinnerOption)
    }

    fun list():List<SpinnerOption>{
      return  filterItemRepository.findAll()
    }


    fun delete(id: Long){
        filterItemRepository.deleteById(id)
    }

    fun findById(id: Long):SpinnerOption{
      return  filterItemRepository.findById(id).get()
    }


    fun findAllByType(type:String):List<SpinnerOption>{
        return filterItemRepository.findAllByTypeAndParentId(type,"")
    }

    fun findAllByParentId(parentId:String):List<SpinnerOption>{

        return filterItemRepository.findAllByParentId(parentId)
    }


    fun findAllByTypeAndParentId(type: String,parentId:String):List<SpinnerOption>{

        return filterItemRepository.findAllByTypeAndParentId(type,parentId)
    }


    fun  findAllAndChildByType(type: String):List<OptionsVO>{
         val list=  filterItemRepository.findAllByTypeAndParentId(type,"")
         val dtoList=ArrayList<OptionsVO>()
        for(item in list) {
            val dto = OptionsVO()
            BeanUtils.copyProperties(item, dto)
            dto.child = findAllByParentId(item.id.toString())
            dtoList.add(dto)
        }

        return dtoList
    }


}
