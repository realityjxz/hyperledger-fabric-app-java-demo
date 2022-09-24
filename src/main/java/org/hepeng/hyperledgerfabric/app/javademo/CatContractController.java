package org.hepeng.hyperledgerfabric.app.javademo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * author he peng
 * date 2022/1/22 21:34
 */

@RestController
@RequestMapping("/cat")
@Slf4j
@AllArgsConstructor
public class CatContractController {

   final Gateway gateway;


   @GetMapping("/{key}")
   public Map<String , Object> queryCatByKey(@PathVariable String key) throws ContractException {

       Map<String , Object> result = Maps.newConcurrentMap();
       Contract contract = getContract();
       byte[] cat = contract.evaluateTransaction("queryCat", key);

       result.put("payload" , StringUtils.newStringUtf8(cat));
       result.put("status" , "ok");

       return result;
   }

    @PutMapping("/")
    public Map<String , Object> createCat(@RequestBody CatDTO cat) throws ContractException, TimeoutException, InterruptedException {

        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();
        byte[] bytes = contract.submitTransaction("createCat", cat.getKey(), cat.getName(), String.valueOf(cat.getAge()), cat.getColor(), cat.getBreed());

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        return result;
    }

    @PostMapping("/")
    public Map<String , Object> updateCat(@RequestBody CatDTO cat) throws ContractException, TimeoutException, InterruptedException {

        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();
        byte[] bytes = contract.submitTransaction("updateCat", cat.getKey(), cat.getName(), String.valueOf(cat.getAge()), cat.getColor(), cat.getBreed());

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        return result;
    }

    @DeleteMapping("/{key}")
    public Map<String , Object> deleteCatByKey(@PathVariable String key) throws ContractException, TimeoutException, InterruptedException {

        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();
        byte[] cat = contract.submitTransaction("deleteCat", key);

        result.put("payload" , StringUtils.newStringUtf8(cat));
        result.put("status" , "ok");

        return result;
    }

   private Contract getContract() {

       // 获取通道
       Network network = gateway.getNetwork("mychannel");

       // 获取合约
       return network.getContract("hyperledger-fabric-contract-java-demo");
   }
}
