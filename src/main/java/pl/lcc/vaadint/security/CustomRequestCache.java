/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lcc.vaadint.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author piko
 */


/**
 * HttpSessionRequestCache that avoids saving internal framework requests.
 */
class CustomRequestCache extends HttpSessionRequestCache {
	/**
	 * {@inheritDoc}
	 *
	 * If the method is considered an internal request from the framework, we skip
	 * saving it.
	 *
	 * @see SecurityUtils#isFrameworkInternalRequest(HttpServletRequest)
	 */
	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
		if (!SecurityUtils.isFrameworkInternalRequest(request)) {
			super.saveRequest(request, response);
		}
	}

}
