package com.ruifucredit.cloud.inventory.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruifucredit.cloud.inventory.service.IStockService;
import com.ruifucredit.cloud.inventory.support.controller.IStockController;
import com.ruifucredit.cloud.inventory.support.dto.Stock;
import com.ruifucredit.cloud.kit.dto.Outcoming;

import lombok.SneakyThrows;

@RestController
public class StockController implements IStockController {
	
	private Logger logger = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private IStockService stockService;
	
	@Override
	public Outcoming<Stock> queryStock(@PathVariable(name = "id") Long id) {

		Stock result = stockService.queryOne(id);

		return new Outcoming<Stock>(result);

	}

	@Override
	@SneakyThrows
	public Outcoming<List<Stock>> queryCommodityStock(@PathVariable(name = "id") Long id) {

		List<Stock> result = stockService.queryByGoodsId(id);

		logger.info("StockController.queryCommodityStock: {}, result: {}", id, result);

		Thread.sleep(new Random(new Date().getTime()).nextInt(5)*1000);
		
		return new Outcoming<List<Stock>>(result);

	}

	@Override
	public Outcoming<Stock> queryCommodityStock(@PathVariable(name = "id") Long id,
			@PathVariable(name = "subId") Long subId) {

		Stock result = stockService.queryBySubGoodsId(subId);

		return new Outcoming<Stock>(result);

	}

	@Override
	public Outcoming<Stock> modifyStock(@PathVariable(name = "stockId") Long id, @RequestBody Stock stock) {

		Stock result = stockService.modify(stock, id);

		return new Outcoming<Stock>(result);

	}

	/*
	 * [{"goodsId":2,"subGoodsId":1,"stockNumber":20},{"goodsId":2,"subGoodsId":2,
	 * "stockNumber":35}]
	 */
	@Override
	public Outcoming<List<Stock>> createStock(@RequestBody List<Stock> stock) {

		List<Stock> result = stockService.create(stock);

		return new Outcoming<List<Stock>>(result);
	}

	@Override
	public Outcoming<Stock> removeStock(@PathVariable(name = "stockId") Long id) {

		Stock result = stockService.queryOne(id);

		if (result != null) {
			stockService.remove(id);
		}

		return new Outcoming<Stock>(result);

	}

}
