package com.vaistramanagement.vaistramanagement.utils;

import com.vaistramanagement.vaistramanagement.dto.*;
import com.vaistramanagement.vaistramanagement.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component


public class AppUtils {



    private final ModelMapper modelMapper;

    @Autowired
    public AppUtils(ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }

    //---------------------------------------------------EMAIL UTILS----------------------------------------------------

//    public static String getEmailMessage(String name, String host, String token)
//    {
//        return "Hello, "+name+",\n\n Your new account has been created. Please click the link below to verify your account. \n\n"
//                +getVerificationURL(host, token)+"\n\n Team VaistraTech.";
//    }
//
//    public static String getVerificationURL(String host, String token)
//    {
//        return host+"/user/verify?token="+token;
//    }




    //---------------------------------------------------USER UTILS-----------------------------------------------------
    public UserDto userToDto(User user)
    {
        return modelMapper.map(user, UserDto.class);
    }
    public User dtoToUser(UserDto userDto)
    {
        return modelMapper.map(userDto, User.class);
    }

    public List<UserDto> usersToDtos(List<User> users) {
        java.lang.reflect.Type targetListType = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(users, targetListType);
    }


    //---------------------------------------------------COUNTRY UTILS--------------------------------------------------
    public CountryDto countryToDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }

    public Country dtoToCountry(CountryDto dto) {
        return modelMapper.map(dto, Country.class);    }

    public List<CountryDto> countriesToDtos(List<Country> countries) {
        java.lang.reflect.Type targetListType = new TypeToken<List<CountryDto>>() {}.getType();
        return modelMapper.map(countries, targetListType);
    }

    public List<Country> dtosToCountries(List<CountryDto> dtos) {
        java.lang.reflect.Type targetListType = new TypeToken<List<Country>>() {}.getType();
        return modelMapper.map(dtos, targetListType);
    }


    //---------------------------------------------------STATE UTILS----------------------------------------------------
    public StateDto stateToDto(State state) {
        return modelMapper.map(state, StateDto.class);
    }

    public State dtoToState(StateDto dto) {
        return modelMapper.map(dto, State.class);    }

    public List<StateDto> statesToDtos(List<State> states) {
        java.lang.reflect.Type targetListType = new TypeToken<List<StateDto>>() {}.getType();
        return modelMapper.map(states, targetListType);
    }

    public List<State> dtosToStates(List<StateDto> dtos) {
        java.lang.reflect.Type targetListType = new TypeToken<List<State>>() {}.getType();
        return modelMapper.map(dtos, targetListType);
    }


    //---------------------------------------------------DISTRICT UTILS-------------------------------------------------

    public DistrictDto districtToDto(District district) {
        return modelMapper.map(district, DistrictDto.class);
    }

    public District dtoToDistrict(DistrictDto dto) {
        return modelMapper.map(dto, District.class);
    }

    public List<DistrictDto> districtsToDtos(List<District> districts) {
        java.lang.reflect.Type targetListType = new TypeToken<List<DistrictDto>>() {}.getType();
        return modelMapper.map(districts, targetListType);
    }


    // SubDistrict Utils

    public SubDistrictDto subdistrictToDto(SubDistrict subdistrict) {
        return modelMapper.map(subdistrict, SubDistrictDto.class);
    }

    public SubDistrict dtoTosubDistrict(SubDistrictDto dto) {
        return modelMapper.map(dto, SubDistrict.class);
    }

    public List<SubDistrictDto>subdistrictsToDtos(List<SubDistrict> subdistricts) {
        java.lang.reflect.Type targetListType = new TypeToken<List<SubDistrictDto>>() {}.getType();
        return modelMapper.map(subdistricts, targetListType);
    }

//    // Village Utils

//    public VillageDto villageToDto(List<Village> village) {
//        return modelMapper.map(village, VillageDto.class);
//    }

//    public VillageDto villageToDto(Village village ) {
//        return modelMapper.map(village, VillageDto.class);
//    }
//
//    public Village dtoTosubvillage(VillageDto dto) {
//        return modelMapper.map(dto, Village.class);
//    }
//
////    public List<VillageDto>villagesToDtos(List<Village> villages) {
////        java.lang.reflect.Type targetListType = new TypeToken<List<VillageDto>>() {}.getType();
////        return modelMapper.map(villages, targetListType);
////    }
//    public List<VillageDto> villagesToDtos(List<Village> village) {
//        java.lang.reflect.Type targetListType = new TypeToken<List<VillageDto>>() {}.getType();
//        return modelMapper.map(village, targetListType);
//    }

//    public List<VillageDto> villageToDtos(List<Village> villages) {
//        java.lang.reflect.Type targetListType = new TypeToken<List<VillageDto>>() {}.getType();
//        return modelMapper.map(villages, targetListType);
//    }


    public VillageDto villageToDto(Village village)
    {
        return modelMapper.map(village,VillageDto.class);
    }

    public List<VillageDto> villagesToDtos(List<Village> villages)
    {
        java.lang.reflect.Type targetListType = new TypeToken<List<VillageDto>>() {}.getType();
        return modelMapper.map(villages, targetListType);
    }


    public MineralDto mineralToDto(Mineral m) {
        return modelMapper.map(m,MineralDto.class);
    }

    public List<MineralDto> mineralsToDtos(List<Mineral> minerals) {
        java.lang.reflect.Type targetListType = new TypeToken<List<MineralDto>>() {}.getType();
        return modelMapper.map(minerals, targetListType);
    }

    public EntityDto entityToDto(EntityType e)
    {
        return modelMapper.map(e,EntityDto.class);
    }
    public List<EntityDto> entitysToDtos(List<EntityType> en) {
        java.lang.reflect.Type targetListType = new TypeToken<List<EntityDto>>() {}.getType();
        return modelMapper.map(en, targetListType);
    }


    public DesignationDto desToDto(Designation d)
    {
        return modelMapper.map(d,DesignationDto.class);
    }
    public List<DesignationDto> desigsToDtos(List<Designation> d) {
        java.lang.reflect.Type targetListType = new TypeToken<List<DesignationDto>>() {}.getType();
        return modelMapper.map(d, targetListType);
    }


    public EquipmentDto equipToDto(Equipment e)
    {
        return modelMapper.map(e,EquipmentDto.class);

    }
    public List<EquipmentDto> equipsToDtos(List<Equipment> e) {
        java.lang.reflect.Type targetListType = new TypeToken<List<EquipmentDto>>() {}.getType();
        return modelMapper.map(e, targetListType);
    }

    public VehicleDto vehicleToDto(Vehicle v)
    {
        return modelMapper.map(v,VehicleDto.class);

    }
    public List<VehicleDto> vehiclesToDtos(List<Vehicle> v) {
        java.lang.reflect.Type targetListType = new TypeToken<List<VehicleDto>>() {}.getType();
        return modelMapper.map(v, targetListType);
    }

    public BankDto bankToDto(Bank bank)
    {
        return modelMapper.map(bank, BankDto.class);
    }

    public List<BankDto> banksToDtos(List<Bank> bank)
    {
        java.lang.reflect.Type targetListType = new TypeToken<List<BankDto>>() {}.getType();
        return modelMapper.map(bank, targetListType);
    }


    public BankBranchDto bankbranchToDto(BankBranch branch)
    {
        return modelMapper.map(branch,BankBranchDto.class);
    }

    public List<BankBranchDto> bankbranchesToDtos(List<BankBranch> branch)
    {
        java.lang.reflect.Type targetListType = new TypeToken<List<BankBranchDto>>() {}.getType();
        return modelMapper.map(branch, targetListType);
    }


}