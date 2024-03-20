//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@RestController
public class TestController{
    @GetMapping("/test")
    public String test(){
        return "Hello";
    }
}