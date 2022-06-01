package com.example.blfood.Storage;

//import com.example.blfood.Model.HorizontalItem;
//import com.example.blfood.Model.ItemCart;

import com.example.blfood.Model.HorizontalItem;
import com.example.blfood.Model.ItemCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCart implements Serializable {
    public static List<ItemCart> itemCartList = new ArrayList<ItemCart>();
    public static int soluongmua;
    public static int tonggia;

    public static void InsertItemCartList(HorizontalItem horizontalItem) {
        if (MyCart.itemCartList.size() > 0) {
            boolean TonTaiMatHang = false;
            for (int i = 0; i < MyCart.itemCartList.size(); i++) {
                // insert một item mới thì tìm kiếm xem đã có item đó tồn tại trong cart chưa
                // nếu đã tồn tại thì chỉ cần thêm hoặc bớt số lượng và tổng giá của mặt hàng đó trong cart
                // nếu chưa tồn tại thì thêm mặt hàng mới vào 1 element mới của mảng
                if (MyCart.itemCartList.get(i).getIdproduct() == horizontalItem.getIdproduct()) {
                    // tồn tại một element của sản phẩm
                    TonTaiMatHang = true;

                    soluongmua = itemCartList.get(i).getSoluongmua() + 1;
                    tonggia    = itemCartList.get(i).getGia() * soluongmua;

                    // set lại các giá trị mới của mặt hàng có trong cart
                    MyCart.itemCartList.get(i).setSoluongmua(soluongmua);
                    MyCart.itemCartList.get(i).setTonggia(tonggia);
                }
            }
            if (TonTaiMatHang == false) {
                soluongmua = 1;
                tonggia = horizontalItem.getPrice();
                MyCart.itemCartList.add(new ItemCart(horizontalItem.getIdproduct(),
                        horizontalItem.getProductname(),
                        horizontalItem.getPrice(),
                        tonggia,
                        soluongmua,
                        horizontalItem.getItemImageUrl()));
            }
        } else {
            // nếu như mảng rõng, thì đây là sản phẩm đầu tiên mua trong giỏ, nên số lượng mua chắc chắn là 1
            // và tổng giá cũng bằng giá sản phẩm
            soluongmua = 1;
            tonggia   = horizontalItem.getPrice();
            MyCart.itemCartList.add(new ItemCart(horizontalItem.getIdproduct(),
                                        horizontalItem.getProductname(),
                                        horizontalItem.getPrice(),
                                        tonggia,
                                        soluongmua,
                                        horizontalItem.getItemImageUrl()));
        }
    }

    // giảm bớt 1 số lượng sản phẩm trong mảng
    public static void DeleteOneItemCart(ItemCart itemCart) {
        for (int i = 0; i < MyCart.itemCartList.size(); i++) {
            if (MyCart.itemCartList.get(i).getIdproduct() == itemCart.getIdproduct()) {
                soluongmua = MyCart.itemCartList.get(i).getSoluongmua() - 1;
                tonggia    = MyCart.itemCartList.get(i).getGia() * soluongmua;

                // set lại các giá trị của product tại element đó trong mảng
                MyCart.itemCartList.get(i).setSoluongmua(soluongmua);
                MyCart.itemCartList.get(i).setTonggia(tonggia);
            }
        }
    }
    // xóa tất cả 1 loại sản phẩm nào đó có trong mảng
    public static void DeleteAllTypeItem(ItemCart itemCart) {
        for (int i = 0; i < MyCart.itemCartList.size(); i++) {
            if (MyCart.itemCartList.get(i).getIdproduct() == itemCart.getIdproduct()) {
                MyCart.itemCartList.remove(i);
            }
        }
    }
    // xóa tất cả các sản phẩm có trong mảng
    public static void DeleteAllCart() {
        MyCart.itemCartList.clear();
    }
}
