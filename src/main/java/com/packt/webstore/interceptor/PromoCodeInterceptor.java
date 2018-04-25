package com.packt.webstore.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PromoCodeInterceptor extends HandlerInterceptorAdapter {
	
	private String promoCode;
	private String errorRedirect;
	private String offerRedirect;
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		String givenPromoCode = req.getParameterValues("promo") == null ? "" : req.getParameterValues("promo")[0];
		
		if (req.getRequestURI().endsWith("products/specialOffer")) {
			if (givenPromoCode.equals(promoCode)) {
				res.sendRedirect(req.getContextPath() +"/"+ offerRedirect);
			} else {
				res.sendRedirect(errorRedirect);
			}
			return false;
		}
		return true;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getErrorRedirect() {
		return errorRedirect;
	}

	public void setErrorRedirect(String errorRedirect) {
		this.errorRedirect = errorRedirect;
	}

	public String getOfferRedirect() {
		return offerRedirect;
	}

	public void setOfferRedirect(String offerRedirect) {
		this.offerRedirect = offerRedirect;
	}
	
}
