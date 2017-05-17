package com.zyf.other.pool.test.factory;

import com.zyf.commons.entity.sys.User;
import com.zyf.other.pool.ObjectFactory;

public class PoolEntityObjectFactory implements ObjectFactory {

		@Override
		public Object create() {
			return new User();
		}
	}