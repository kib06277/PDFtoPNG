package com.example.pdftopng;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.document.PdfDocumentLoader;

//PDF 轉 PNG
public class MainActivity extends AppCompatActivity {

    ConstraintLayout layoutbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //將 xml 和程式關聯
        layoutbg = findViewById(R.id.layoutbg);

        //開啟系統預設檔案選擇器
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,0);
    }

    //處理選擇器回傳
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            try {
                // 為文件創建一個 Uri。
                Uri uri = data.getData();

                //從 PDF 文件的 URL 實例化
                final PdfDocument document = PdfDocumentLoader.openDocument(this, uri);

                //規劃尺寸
                final int pageIndex = 0;
                final RectF pageImageSize = document.getPageSize(pageIndex).toRect();

                // 創建圖像
                Bitmap bitmap = document.renderPageToBitmap(this, pageIndex, (int)pageImageSize.width() , (int)pageImageSize.height());

                //將 bitmap 轉圖檔
                Drawable drawable = new BitmapDrawable(bitmap);

                //設定背景
                layoutbg.setBackground(drawable);

                Toast.makeText(this, uri.getPath(), Toast.LENGTH_LONG).show();
            } catch(final Exception exception) {
                // Handle error.
            }
        } else {
            Toast.makeText(this, "取消選擇檔案!!", Toast.LENGTH_LONG).show();
        }
    }
}