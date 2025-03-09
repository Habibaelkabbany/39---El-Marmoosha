package com.example.controller;

public class UserController {
<<<<<<< Updated upstream
=======
//The Dependency Injection Variables
//The Constructor with the requried variables mapping the Dependency Injection.
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;
    public UserController(UserService userService,CartService cartService,ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }
    @PostMapping("/")
    public User addUser(@RequestBody User user){
          return  userService.addUser(user);
    }

    @GetMapping("/")
    public ArrayList<User> getUsers(){
       return userService.getUsers();
    }
    
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);
    }
    @GetMapping("/{userId}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID userId){
         return userService.getOrdersByUserId(userId);
    }
    @PostMapping("/{userId}/checkout")
    public String addOrderToUser(@PathVariable UUID userId){
         userService.addOrderToUser(userId);
         return "Order added";
    }
    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId){
        userService.removeOrderFromUser(userId, orderId);
        return "Order removed";
    }
    @DeleteMapping("/{userId}/emptyCart")
    public String emptyCart(@PathVariable UUID userId){
        userService.emptyCart(userId);
        return "Cart empty";
    }
    @PutMapping("/addProductToCart")
    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId){
        Cart cart = cartService.getCartByUserId( userId);
        UUID cartId = cart.getId();
        Product product = productService.getProductById(productId);

        cartService.addProductToCart(cartId, product);
        return "Product added";
    }
    @PutMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId){
        Cart cart = cartService.getCartByUserId(userId);
        UUID cartId = cart.getId();
        Product product = productService.getProductById(productId);

        cartService.deleteProductFromCart(cartId, product);
        return "Product added";
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId){
        userService.deleteUserById(userId);
        return "User deleted";
    }




>>>>>>> Stashed changes
}
