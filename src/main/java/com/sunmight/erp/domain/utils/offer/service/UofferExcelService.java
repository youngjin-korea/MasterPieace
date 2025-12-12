package com.sunmight.erp.domain.utils.offer.service;

import com.sunmight.erp.domain.utils.offer.dto.request.OfferDetailRequest;
import com.sunmight.erp.domain.utils.offer.entity.UofferDetailEntity;
import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import com.sunmight.erp.domain.utils.offer.repository.UofferDetailRepository;
import com.sunmight.erp.domain.utils.offer.repository.UofferRepository;
import com.sunmight.erp.global.ExcelCellUtils;
import com.sunmight.erp.global.exception.EntityNotFoundBusinessException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UofferExcelService {

    private final UofferRepository uofferRepository;
    private final UofferDetailRepository uofferDetailRepository;

    @Transactional
    public void uploadUofferDetails(Long offerId, MultipartFile file) {

        // offer가 존재하지 않으면 예외발생
        UofferEntity findOffer = uofferRepository.findById(offerId)
                .orElseThrow(() -> {
                    log.info("찾는 U_OFFER: {}", offerId);
                    return new EntityNotFoundBusinessException("Uoffer", offerId);
                });

        try (InputStream inputStream = file.getInputStream();
                XSSFWorkbook sheets = new XSSFWorkbook(inputStream)) {

            XSSFSheet firstSheet = sheets.getSheetAt(0);

            List<UofferDetailEntity> entities = new ArrayList<>();
            for (Row row : firstSheet) {
                // 첫 행 컬럼명들 스킵
                if (row.getRowNum() == 0) {
                    continue;
                }

                UofferDetailEntity parsedEntity = UofferDetailEntity.builder()
                        .itemTitle(ExcelCellUtils.getString(row.getCell(0)))
                        .modelName(ExcelCellUtils.getString(row.getCell(1)))
                        .spec(ExcelCellUtils.getString(row.getCell(2)))
                        .quantity(ExcelCellUtils.getInteger(row.getCell(3)))
                        .supplyPrice(ExcelCellUtils.getBigDecimal(row.getCell(4)))
                        .remark(ExcelCellUtils.getString(row.getCell(5))).build();

                parsedEntity.setOffer(findOffer);

                entities.add(parsedEntity);
            }
            // Batch Insert 처리
            uofferDetailRepository.saveAll(entities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] generateTemplate() {

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("UofferDetailTemplate");

            // Header Row 생성
            Row header = sheet.createRow(0);
            String[] columns = {
                    "itemTitle",
                    "modelName",
                    "spec",
                    "quantity",
                    "supplyPrice",
                    "remark"
            };

            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
                sheet.autoSizeColumn(i);
            }

            // 샘플 데이터(Optional)
            Row sample = sheet.createRow(1);
            sample.createCell(0).setCellValue("연마지 400G");
            sample.createCell(1).setCellValue("SM-400");
            sample.createCell(2).setCellValue("240 x 300mm");
            sample.createCell(3).setCellValue(3000);
            sample.createCell(4).setCellValue(4000);
            sample.createCell(5).setCellValue("비고 예시");

            // 워크북을 ByteArray로 반환
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            return bos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Excel 템플릿 생성 실패", e);
        }
    }


}
