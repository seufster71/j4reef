/*
 * Copyright (C) 2020 The ToastHub Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Edward H. Seufert
 */

package org.toasthub.j4reef.model;

import org.toasthub.j4reef.utils.Request;
import org.toasthub.j4reef.utils.Response;

public interface BaseSvc {

	public void process(Request request, Response response);
	public void itemCount(Request request, Response response);
	public void item(Request request, Response response);
	public void items(Request request, Response response);
	public void delete(Request request, Response response);
	public void save(Request request, Response response);
}
