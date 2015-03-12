/*
 * Copyright 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package helpscout.controller;

import helpscout.resource.Customer;

import org.springframework.stereotype.Controller;

/**
 * @author caio.amaral
 *
 */
@Controller
public class CustomerController {
	public Customer retrieve(final Integer id) {
		return new Customer(id);
	}

	public Customer update(final Integer id) {
		return new Customer(id);
	}

	public Customer delete(final Integer id) {
		return new Customer(id);
	}
}
