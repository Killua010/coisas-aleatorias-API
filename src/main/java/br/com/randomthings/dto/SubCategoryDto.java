package br.com.randomthings.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.randomthings.domain.Category;
import br.com.randomthings.domain.DomainEntity;
import br.com.randomthings.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class SubCategoryDto extends EntityDto {
	
	@NotNull(message="O nome da subcategoria é obrigatório")
	@NotEmpty(message="O nome da subcategoria é obrigatório")
	private String name;
	
	private CategoryDto categoryDto;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotNull(message="O id da categoria é obrigatório")
	@Min(value=1, message="O id informado é invalido")
	private Long categoryId;

	@Override
	public DomainEntity getEntity() {
		SubCategory subCategory = new SubCategory();
		Category category = new Category();
		category.setId(categoryId);
		subCategory.setName(name);
		subCategory.setCategory(category);
		return subCategory;
	}
	
	@Override
	public DomainEntity getEntity(Long id) {
		Boolean status = (null == this.status) ? null : this.status;
		SubCategory subCategory = new SubCategory();
		Category category = new Category();
		category.setId(categoryId);
		subCategory.setName(name);
		subCategory.setId(id);
		subCategory.setStatus(status);
		subCategory.setCategory(category);
		return subCategory;
	}

	@Override
	public List<EntityDto> getListDto(List<DomainEntity> entities) {
		List<EntityDto> dtos = new ArrayList<EntityDto>();
		for(DomainEntity entity : entities) {
			dtos.add(new SubCategoryDto().getDTO(entity));
		}
		return dtos;
	}

	@Override
	public EntityDto getDTO(DomainEntity subCategory) {
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		subCategoryDto.setId(subCategory.getId());
		subCategoryDto.setStatus(subCategory.getStatus());
		subCategoryDto.setCreationDate(subCategory.getCreationDate());
		subCategoryDto.setLastUpdate(subCategory.getLastUpdate());
		subCategoryDto.setName(((SubCategory)subCategory).getName());
		subCategoryDto.setCategoryDto((CategoryDto) new CategoryDto().getDTO(((SubCategory)subCategory).getCategory()));
		return subCategoryDto;
	}

}
