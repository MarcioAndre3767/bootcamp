package com.mam.bootcamp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mam.bootcamp.exceptions.BusinessException;
import com.mam.bootcamp.exceptions.NotFoundException;
import com.mam.bootcamp.mapper.StockMapper;
import com.mam.bootcamp.model.Stock;
import com.mam.bootcamp.model.dto.StockDTO;
import com.mam.bootcamp.repository.StockRepository;
import com.mam.bootcamp.util.MessageUtils;

@Service
public class StockService {
	
	
	
	@Autowired
	private StockRepository repository;
	
	
	@Autowired
	private StockMapper mapper;
	
	
	@Transactional
	public StockDTO save(@Valid StockDTO dto) {
		
		Optional<Stock> optionalStock = repository.findByNameAndDate( dto.getName(), dto.getDate() );
		
		if( optionalStock.isPresent() ) {
			throw new BusinessException( MessageUtils.STOCK_ALREADY_EXISTS );
		}
		
		
		Stock stock = mapper.toEntity( dto );		
		repository.save(stock);
		dto.setId( stock.getId() );
		
		return mapper.toDto( stock );
	}

	
	@Transactional
	public StockDTO update(@Valid StockDTO dto) {
		Optional<Stock> optionalStock = repository.findByestockUpdate( dto.getName(), dto.getDate(), dto.getId() );
		
		if( optionalStock.isPresent() ) {
			throw new BusinessException( MessageUtils.STOCK_ALREADY_EXISTS );
		}		
		
		Stock stock  = mapper.toEntity(dto);
		repository.save(stock);
		
		return mapper.toDto(stock);
	}


	@Transactional
	public StockDTO delete(Long id) {
		StockDTO dto = this.findById(id);
		repository.deleteById(dto.getId());
		
		return dto;
	}
		
	
	
	@Transactional
	public List<StockDTO> findall() {		
		return mapper.toDto(repository.findAll());
	}

	
	
	@Transactional
	public StockDTO findById(Long id) {
	
		return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
	}


	@Transactional
	public List<StockDTO> findByToday() {
		
		return repository.findByToday(LocalDate.now()).map(mapper::toDto).orElseThrow(NotFoundException::new);
		
	}



	
	

}










