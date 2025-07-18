// src/test/java/com/imeal/imeal_back/shop/builder/ShopBuilder.java
package com.imeal.imeal_back.shop.builder;

import static com.imeal.imeal_back.base.builder.BaseBuilder.aBase;
import com.imeal.imeal_back.base.entity.Base;
import static com.imeal.imeal_back.location.builder.LocationBuilder.aLocation;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.shop.entity.Shop;

public class ShopBuilder {

    private String name = "テスト店舗";
    private String url = "http://example.com";
    private String address = "東京都テスト区1-1-1";
    private Base base = aBase().build(); // デフォルトでBaseオブジェクトを持つ
    private Location location = aLocation().build(); // デフォルトでLocationオブジェクトを持つ

    public static ShopBuilder aShop() {
        return new ShopBuilder();
    }

    public ShopBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    // 依存するオブジェクトを外部から設定できるようにする
    public ShopBuilder withBase(Base base) {
        this.base = base;
        return this;
    }

    public ShopBuilder withLocation(Location location) {
        this.location = location;
        return this;
    }

    public Shop build() {
        Shop shop = new Shop();
        shop.setName(this.name);
        shop.setUrl(this.url);
        shop.setAddress(this.address);
        shop.setDistance(100);
        shop.setMinutes(5);
        shop.setBase(this.base); // 設定されたBaseオブジェクトをセット
        shop.setLocation(this.location); // 設定されたLocationオブジェクトをセット
        return shop;
    }
}
