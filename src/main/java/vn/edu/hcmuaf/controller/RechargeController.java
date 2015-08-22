package vn.edu.hcmuaf.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.service.LoginServiceImpl;
import vn.edu.hcmuaf.util.LinkService;

@Controller
@RequestMapping("/RechargeController")
public class RechargeController {
	@Autowired
	private LoginServiceImpl userService;

	@RequestMapping(value = "/addRecharge", method = RequestMethod.POST)
	public @ResponseBody
	String doAddRecharge(@RequestParam("email") String email,
			@RequestParam("seri") String seri) {
		String message = "FAIL";
		Map<String, String> params = new HashMap<String, String>();
		params.put("seri", seri);
		params.put("email", email);
		RestTemplate restTemplate = new RestTemplate();

		try {
			message = restTemplate.getForObject(LinkService.RECHARGE_ADD,
					String.class, params);
		} catch (Exception e) {
			System.out.println("Null Exception!");
			return null;
		}
		return message;
	}

	@RequestMapping(value = "/getRecharges/{email}/user", method = RequestMethod.GET)
	public @ResponseBody
	String doGetListRecharge(@PathVariable("email") String email) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		RestTemplate restTemplate = new RestTemplate();

		String str = restTemplate.getForObject(LinkService.RECHARGE_GETBY_USER,
				String.class, params);
		return str;
	}

	// private boolean addRecharge(User user, int value) {
	// RechargeCard recharge = new RechargeCard();
	// recharge.setCreatedDate(new Date());
	// recharge.setUser(user);
	// recharge.setValue(value);
	//
	// RestTemplate restTemplate = new RestTemplate();
	// ResponseEntity<Void> result = restTemplate.postForEntity(
	// LinkService.RECHARGE_ADD, recharge, Void.class);
	// if (result.getStatusCode().equals(HttpStatus.OK)) {
	// return true;
	// }
	// return false;
	// }
	//
	// private boolean updateUser(User user) {
	// RestTemplate restTemplate = new RestTemplate();
	// ResponseEntity<Void> result = restTemplate.postForEntity(
	// LinkService.USER_UPDATE, user, Void.class);
	// if (result.getStatusCode().equals(HttpStatus.OK)) {
	// return true;
	// }
	// return false;
	// }
	//
	// private boolean updateCard(Card card) {
	// RestTemplate restTemplate = new RestTemplate();
	// ResponseEntity<Void> result = restTemplate.postForEntity(
	// LinkService.CARD_UPDATE, card, Void.class);
	// if (result.getStatusCode().equals(HttpStatus.OK)) {
	// return true;
	// }
	// return false;
	// }
	//
	// private Card getCard(String seri) {
	// Card card = null;
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("seri", seri);
	// RestTemplate restTemplate = new RestTemplate();
	//
	// try {
	// card = restTemplate.getForObject(LinkService.CARD_GETBY_SERI,
	// Card.class, params);
	// } catch (Exception e) {
	// System.out.println("Null Exception!");
	// return null;
	// }
	// return card;
	// }

}
