# Security Notes
- App does not store raw PAN.
- App stores only panSha256 from payment software.
- panSha256 is treated as sensitive identifier.
- Waiter PIN is stored as salted slow hash (PBKDF2 suggested).
- Final PCI DSS architecture must be reviewed by security/payment provider specialists.
- Do not store panSha256 near additional card data unless required by business logic.
