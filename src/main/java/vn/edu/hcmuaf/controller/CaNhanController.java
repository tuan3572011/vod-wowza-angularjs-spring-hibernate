package vn.edu.hcmuaf.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA. User: xvitcoder Date: 12/20/12 Time: 5:27 PM
 */
@Controller
@RequestMapping("/CaNhanController")
public class CaNhanController {

	@RequestMapping(value = "/layout")
	public String getIndexPage(Map<String, Object> map) {
		return "canhan";
	}

	@RequestMapping(value = "/thongtin/layout", method = RequestMethod.GET)
	public String doGetLayOutThongTin() {
		return "thongtincanhan";
	}

	@RequestMapping(value = "/recharge/layout", method = RequestMethod.GET)
	public String doGetLayOutRecharge() {
		return "recharge";
	}

	@RequestMapping(value = "/history/layout", method = RequestMethod.GET)
	public String doGetLayOutHistory() {
		return "historyRecharge";
	}

}
