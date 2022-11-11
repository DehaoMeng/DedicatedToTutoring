package com.example.myapplication;

import com.example.myapplication.slice.*;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.security.SystemPermission;

import java.util.ArrayList;
import java.util.List;

public class SliderPageAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SliderPageAbilitySlice.class.getName());
        addActionRoute("award",AwardSlice.class.getName());
        requestPermissions();

    }
    private void requestPermissions(){
        String[] permissions = {
                SystemPermission.LOCATION,
                SystemPermission.LOCATION_IN_BACKGROUND
        };
        List<String> permissionsToprocess = new ArrayList<>();
        for (String permission: permissions){
            if (verifySelfPermission(permission) != 0 && canRequestPermission(permission)){
                permissionsToprocess.add(permission);
            }
        }
        requestPermissionsFromUser(permissionsToprocess.toArray(new String[0]),0);
    }
}
