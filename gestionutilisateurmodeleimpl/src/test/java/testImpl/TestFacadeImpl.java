package testImpl;


import serviceImpl.facadeImpl.FabriqueFacadeImpl;
import testInterfaces.TestFacadeAll;

public class TestFacadeImpl extends TestFacadeAll {

    public TestFacadeImpl() {
        super(new FabriqueFacadeImpl());
    }
}
