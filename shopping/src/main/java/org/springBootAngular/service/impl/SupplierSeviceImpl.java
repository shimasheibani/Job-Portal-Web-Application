package org.springBootAngular.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.SupplierDTO;
import org.springBootAngular.dto.UserDTO;
import org.springBootAngular.entity.Supplier;
import org.springBootAngular.exception.NotFoundException;
import org.springBootAngular.repository.CategoryRepository;
import org.springBootAngular.repository.SupplierRepository;
import org.springBootAngular.service.SupplierService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierSeviceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;


    @Override
    public Response saveSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
        supplierRepository.save(modelMapper.map(supplierDTO, Supplier.class));
        return Response.builder()
                .status(200)
                .message("Supplier successfully added")
                .build();
    }

    @Override
    public Response getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<SupplierDTO> supplierDTOs = modelMapper.map(suppliers, new TypeToken<List<SupplierDTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message("All Supplier successfully retrieved")
                .suppliers(supplierDTOs)
                .build();
    }
    @Override
    public Response getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()-> new NotFoundException("Supplier does not found"));
        SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
        return Response.builder()
                .status(200)
                .message("Supplier successfully retrieved")
                .supplier(supplierDTO)
                .build();
    }

    @Override
    public Response updateSupplier(Long id, SupplierDTO supplierDto) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()->new NotFoundException("Supplier does not found"));
        if(supplierDto.getName() != null){
            supplier.setName(supplierDto.getName());
        }
        if(supplierDto.getAddress() != null){
            supplier.setAddress(supplierDto.getAddress());
        }
        if(supplierDto.getContactInfo() != null){
            supplier.setContactInfo(supplierDto.getContactInfo());
        }
        supplierRepository.save(supplier);
        return Response.builder()
                .status(200)
                .message("Supplier successfully updated")
                .build();
    }

    @Override
    public Response deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()->new NotFoundException("Supplier does not found"));
        supplierRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Supplier successfully deleted")
                .build();
    }
}
