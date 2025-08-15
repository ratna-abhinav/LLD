package paymentRecommendationApp;
import java.util.*;
import java.util.stream.Collectors;

enum LineOfBusiness {
    CREDIT_CARD_BILL_PAYMENT,
    COMMERCE,
    INVESTMENT,
    ELECTRICITY_BILL,
    // Add more LOBs as needed
}

enum PaymentInstrumentType {
    CREDIT_CARD,
    UPI,
    DEBIT_CARD,
    NET_BANKING,
    WALLET,
    // Add more types as needed
}

class PaymentInstrument {
    private String id;
    private PaymentInstrumentType type;
    private String issuer;
    private double relevanceScore;

    public PaymentInstrument(String id, PaymentInstrumentType type, String issuer, double relevanceScore) {
        this.id = id;
        this.type = type;
        this.issuer = issuer;
        this.relevanceScore = relevanceScore;
    }

    public String getId() { return id; }
    public PaymentInstrumentType getType() { return type; }
    public double getRelevanceScore() { return relevanceScore; }

    @Override
    public String toString() {
        return "PaymentInstrument{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", issuer='" + issuer + '\'' +
                ", relevanceScore=" + relevanceScore +
                '}';
    }
}

// Cart class (with items)
class Cart {
    private LineOfBusiness lineOfBusiness;
    private double totalAmount;
    private List<String> cartItems; // List of items in the cart

    public Cart(LineOfBusiness lineOfBusiness, double totalAmount, List<String> cartItems) {
        this.lineOfBusiness = lineOfBusiness;
        this.totalAmount = totalAmount;
        this.cartItems = cartItems;
    }

    public LineOfBusiness getLineOfBusiness() { return lineOfBusiness; }
    public double getTotalAmount() { return totalAmount; }
    public List<String> getCartItems() { return cartItems; }
}

// User Context class
class UserContext {
    private boolean isUpiEnabled;

    public UserContext(boolean isUpiEnabled) {
        this.isUpiEnabled = isUpiEnabled;
    }

    public boolean isUpiEnabled() { return isUpiEnabled; }
}

// User class
class User {
    private String userId;
    private UserContext userContext;
    private List<PaymentInstrument> paymentInstruments;

    public User(String userId, UserContext userContext, List<PaymentInstrument> paymentInstruments) {
        this.userId = userId;
        this.userContext = userContext;
        this.paymentInstruments = paymentInstruments;
    }

    public List<PaymentInstrument> getPaymentInstruments() { return paymentInstruments; }
    public UserContext getUserContext() { return userContext; }
}

// PaymentInstrumentRule interface
interface PaymentInstrumentRule {
    boolean isAllowed(double amount, UserContext context);
}

// Rule for UPI
class UpiRule implements PaymentInstrumentRule {
    @Override
    public boolean isAllowed(double amount, UserContext context) {
        return context.isUpiEnabled() && amount <= 200000;  // Example rule for UPI
    }
}

// Rule for Credit Card
class CreditCardRule implements PaymentInstrumentRule {
    @Override
    public boolean isAllowed(double amount, UserContext context) {
        return amount <= 250000;  // Example rule for Credit Cards
    }
}

// Rule for Debit Card
class DebitCardRule implements PaymentInstrumentRule {
    @Override
    public boolean isAllowed(double amount, UserContext context) {
        return amount <= 200000;  // Example rule for Debit Cards
    }
}

// Rule for NetBanking
class NetBankingRule implements PaymentInstrumentRule {
    @Override
    public boolean isAllowed(double amount, UserContext context) {
        return amount <= 150000;  // Example rule for NetBanking
    }
}

// Configuration to hold rules for each payment type
class PaymentRuleConfig {
    private final Map<PaymentInstrumentType, PaymentInstrumentRule> ruleMap = new HashMap<>();

    public PaymentRuleConfig() {
        // Initialize rules for each instrument type
        ruleMap.put(PaymentInstrumentType.UPI, new UpiRule());
        ruleMap.put(PaymentInstrumentType.CREDIT_CARD, new CreditCardRule());
        ruleMap.put(PaymentInstrumentType.DEBIT_CARD, new DebitCardRule());
        ruleMap.put(PaymentInstrumentType.NET_BANKING, new NetBankingRule());
    }

    public PaymentInstrumentRule getRule(PaymentInstrumentType type) {
        return ruleMap.get(type);
    }

    // Dynamically add new payment instrument rules in the future
    public void addRule(PaymentInstrumentType type, PaymentInstrumentRule rule) {
        ruleMap.put(type, rule);
    }
}

// LOB Config to specify allowed payment instruments per LOB
class LobConfig {
    private final Map<LineOfBusiness, Set<PaymentInstrumentType>> lobAllowedInstruments = new HashMap<>();

    public LobConfig() {
        // Define allowed payment instruments for each LOB
        lobAllowedInstruments.put(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, Set.of(PaymentInstrumentType.UPI, PaymentInstrumentType.DEBIT_CARD, PaymentInstrumentType.NET_BANKING));
        lobAllowedInstruments.put(LineOfBusiness.COMMERCE, Set.of(PaymentInstrumentType.CREDIT_CARD, PaymentInstrumentType.UPI, PaymentInstrumentType.DEBIT_CARD));
        lobAllowedInstruments.put(LineOfBusiness.INVESTMENT, Set.of(PaymentInstrumentType.UPI, PaymentInstrumentType.NET_BANKING, PaymentInstrumentType.DEBIT_CARD));
        lobAllowedInstruments.put(LineOfBusiness.ELECTRICITY_BILL, Set.of(PaymentInstrumentType.NET_BANKING, PaymentInstrumentType.UPI)); // New LOB example
    }

    public boolean isInstrumentAllowedForLob(LineOfBusiness lob, PaymentInstrumentType type) {
        return lobAllowedInstruments.getOrDefault(lob, Collections.emptySet()).contains(type);
    }

    // Dynamically add new LOB configurations
    public void addLobAllowedInstruments(LineOfBusiness lob, Set<PaymentInstrumentType> allowedInstruments) {
        lobAllowedInstruments.put(lob, allowedInstruments);
    }
}

// Payment Recommendation Service
class PaymentRecommendationService {
    private final PaymentRuleConfig paymentRuleConfig;
    private final LobConfig lobConfig;

    public PaymentRecommendationService(PaymentRuleConfig config, LobConfig lobConfig) {
        this.paymentRuleConfig = config;
        this.lobConfig = lobConfig;
    }

    public List<PaymentInstrument> recommendPaymentMethods(User user, Cart cart) {
        return user.getPaymentInstruments().stream()
                .filter(instrument -> isInstrumentAllowed(instrument, cart, user.getUserContext()))
                .sorted(Comparator.comparingDouble(PaymentInstrument::getRelevanceScore).reversed())
                .collect(Collectors.toList());
    }

    // Check if the instrument is allowed based on the LOB and UserContext
    private boolean isInstrumentAllowed(PaymentInstrument instrument, Cart cart, UserContext userContext) {
        // First check if the instrument type is allowed for the LOB
        if (!lobConfig.isInstrumentAllowedForLob(cart.getLineOfBusiness(), instrument.getType())) {
            return false;
        }

        // Then check if the payment instrument passes the rule for the instrument type
        PaymentInstrumentRule rule = paymentRuleConfig.getRule(instrument.getType());
        if (rule == null) {
            throw new UnsupportedOperationException("No rule defined for payment type: " + instrument.getType());
        }
        return rule.isAllowed(cart.getTotalAmount(), userContext);
    }
}

// Main class for testing
public class Main {
    public static void main(String[] args) {
        // Creating payment instruments
        PaymentInstrument upi1 = new PaymentInstrument("UPI1", PaymentInstrumentType.UPI, "HDFC", 0.9);
        PaymentInstrument debitCard = new PaymentInstrument("DC1", PaymentInstrumentType.DEBIT_CARD, "ICICI", 0.6);
        PaymentInstrument creditCard = new PaymentInstrument("CC1", PaymentInstrumentType.CREDIT_CARD, "AMEX", 0.8);

        // User Context (UPI enabled)
        UserContext userContext = new UserContext(true);  // UPI enabled

        // User with payment instruments
        User user = new User("user1", userContext, Arrays.asList(upi1, debitCard, creditCard));

        // Cart for Commerce LOB with items
        Cart commerceCart = new Cart(LineOfBusiness.COMMERCE, 120000, Arrays.asList("item1", "item2"));

        // Initialize rule configuration
        PaymentRuleConfig config = new PaymentRuleConfig();

        // Initialize LOB configuration
        LobConfig lobConfig = new LobConfig();

        // Payment recommendation service
        PaymentRecommendationService service = new PaymentRecommendationService(config, lobConfig);

        // Get recommended payment methods
        List<PaymentInstrument> recommendedInstruments = service.recommendPaymentMethods(user, commerceCart);

        // Print recommended instruments
        System.out.println("Recommended Payment Methods:");
        recommendedInstruments.forEach(System.out::println);
    }
}
