package com.nagarro.RouletteAPI.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.RouletteAPI.dto.BlockAmountDTO;
import com.nagarro.RouletteAPI.dto.GameResultDTO;
import com.nagarro.RouletteAPI.services.BlockAmountServices;
import com.nagarro.RouletteAPI.services.GamePlayingServices;
import com.nagarro.RouletteAPI.services.UpdateUserAccountInDBServices;

@Path("play")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GamePlayingController {

	@Autowired
	BlockAmountServices blockAmountServices;

	@Autowired
	GamePlayingServices gamePlayingServices;

	@Autowired
	UpdateUserAccountInDBServices updateUserAccountInDBServices;

	@GET
	@Path("playGame/{customerID}/{blockAmount}/{segmentChosen}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response blockUserAmount(@PathParam("customerID") String customerID,
			@PathParam("blockAmount") double blockAmount, @PathParam("segmentChosen") int segmentChosen) {

		GameResultDTO gameResultDTO = null;

		BlockAmountDTO isEligibleCustomer = blockAmountServices.isValidGameAndBlockAmount(customerID, blockAmount);

		if (isEligibleCustomer.getIsValidGame()) {

			gameResultDTO = gamePlayingServices.calculateGameResult(blockAmount, segmentChosen);

			double finalUserAccountBalance = updateUserAccountInDBServices.updateUserInfoInDB(customerID,
					gameResultDTO);

			gameResultDTO.setFinalUserAccountBalance(finalUserAccountBalance);

			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.header("Access-Control-Max-Age", "1209600").entity(gameResultDTO).build();

		} else {
			return null;
		}

	}

}
