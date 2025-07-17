package com.imeal.imeal_back.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.base.dto.BasesResponse;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BaseService {
  private final BaseMapper baseMapper;
  private final BaseRepository baseRepository;

  public BasesResponse getBases() {
    List<Base> bases = baseRepository.findAll();
    return baseMapper.toResponse(bases);
  }
}
