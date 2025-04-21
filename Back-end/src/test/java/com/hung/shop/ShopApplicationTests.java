package com.hung.shop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

import java.io.File;

@SpringBootTest
class ShopApplicationTests {
	ApplicationModules modules = ApplicationModules.of(ShopApplication.class);
	@Test
	void contextLoads() {
	}

//	@Test
//	void verifyModuleDependenciesAreValid() {
//		modules.verify();
//	}
//	@Test
//	void writeGraphVizFile() {
//		ApplicationModules modules = ApplicationModules.of(ShopApplication.class);
//		modules.writeModulesAsDot(new File("target/modulith-modules.dot"));
//	}
}
