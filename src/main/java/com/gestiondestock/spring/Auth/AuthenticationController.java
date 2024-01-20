package com.gestiondestock.spring.Auth;

import com.gestiondestock.spring.models.ERoles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(APP_ROOT+"/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping(value="/registerWithRoleAndRequestBody/{role}")
    public ResponseEntity<AuthenticationResponse> registerWithRole(@RequestBody RegisterRequest request , @PathVariable("role") String role){
        return ResponseEntity.ok(authenticationService.register(request,role));
    }
    @PostMapping(value="/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(value = "/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }
}
