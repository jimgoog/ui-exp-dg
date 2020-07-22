package com.jetbrains.handson.mpp.mobile

import cnames.structs.sk_surface_t
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.cValue
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.*
import skia.*

fun initialize(view: UIView) {

    val /* sk_surface_t* */ surface = make_surface(640, 480);
    val /* sk_canvas_t* */ canvas = sk_surface_get_canvas(surface)!!;
    draw(canvas);

    val image = UIImage(data = surface.toPngBytes())

    val imageView = UIImageView(image=image)
    imageView.setFrame(CGRectMake(50.0, 50.0, 100.0, 100.0))
    view.addSubview(imageView)

    sk_surface_unref(surface);
}

private fun make_surface(width: Int, height: Int): CPointer<sk_surface_t> {
    val /* sk_imageinfo_t* */ info = sk_imageinfo_new(width, height, sk_colortype_t.RGBA_8888_SK_COLORTYPE,
        sk_alphatype_t.PREMUL_SK_ALPHATYPE, null);
    val /* sk_surface_t* */ result = sk_surface_new_raster(info, null);
    sk_imageinfo_delete(info);
    return result!!;
}

private fun CPointer<sk_surface_t>.toPngBytes(): NSData {
    val /* sk_image_t* */ image = sk_surface_new_image_snapshot(this);
    val /* sk_data_t* */ data = sk_image_encode(image);
    sk_image_unref(image);

    val result = NSData.dataWithBytes(sk_data_get_data(data), sk_data_get_size(data))

    sk_data_unref(data);
    return result;
}

private fun sk_color_set_argb(a: Long, r: Long, g: Long, b: Long) = (((a) shl 24) or ((r) shl 16) or ((g) shl 8) or (b)).toUInt()

private fun draw(canvas: CPointer<sk_canvas_t>) {
    val /* sk_paint_t* */ fill = sk_paint_new();
    sk_paint_set_color(fill, sk_color_set_argb(0xFF, 0x00, 0x00, 0xFF));
    sk_canvas_draw_paint(canvas, fill);

    sk_paint_set_color(fill, sk_color_set_argb(0xFF, 0x00, 0xFF, 0xFF));
    val rect = cValue<sk_rect_t> {
        left = 100.0f;
        top = 100.0f;
        right = 540.0f;
        bottom = 380.0f;
    }
    sk_canvas_draw_rect(canvas, rect, fill);

    val /* sk_paint_t* */ stroke = sk_paint_new();
    sk_paint_set_color(stroke, sk_color_set_argb(0xFF, 0xFF, 0x00, 0x00));
    sk_paint_set_antialias(stroke, true);
    sk_paint_set_stroke(stroke, true);
    sk_paint_set_stroke_width(stroke, 5.0f);
    val /* sk_path_t* */ path = sk_path_new();

    sk_path_move_to(path, 50.0f, 50.0f);
    sk_path_line_to(path, 590.0f, 50.0f);
    sk_path_cubic_to(path, -490.0f, 50.0f, 1130.0f, 430.0f, 50.0f, 430.0f);
    sk_path_line_to(path, 590.0f, 430.0f);
    sk_canvas_draw_path(canvas, path, stroke);

    sk_paint_set_color(fill, sk_color_set_argb(0x80, 0x00, 0xFF, 0x00));
    val rect2 = cValue<sk_rect_t> {
        left = 120.0f;
        top = 120.0f;
        right = 520.0f;
        bottom = 360.0f;
    }
    sk_canvas_draw_oval(canvas, rect2, fill);

    sk_path_delete(path);
    sk_paint_delete(stroke);
    sk_paint_delete(fill);
}


actual fun platformName(): String {

    return UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}
