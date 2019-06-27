package hr.nikola.pdf;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.multipdf.PDFCloneUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDDocumentNameDestinationDictionary;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.PageMode;
import org.apache.pdfbox.pdmodel.common.PDDestinationOrAction;
import org.apache.pdfbox.pdmodel.common.PDNumberTreeNode;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTerminalField;


public class PdfBox {
	private static Logger log = LoggerFactory.getLogger(PdfBox.class);
	private static final File f = new File("C:\\Users\\HrvojeBartolin\\Downloads\\1.pdf");
	private static final File dir = new File("C:\\Users\\HrvojeBartolin\\Downloads\\Sp-7015_2019");
	private static final String STRUCTURETYPE_DOCUMENT = "Document";
	private boolean ignoreAcroFormErrors = false;
	private int nextFieldNum = 1;

	public static void main(String[] args) {
		try {
			new PdfBox().merged2();
//			new PdfBox().merged();
//			new PdfBox().gen();
		} catch (Throwable t) {
			log.error(t.getMessage(), t);
		}
	}

	private void merged2() throws IOException {
		File[] files = dir.listFiles();

		PDDocument destination = new PDDocument(MemoryUsageSetting.setupTempFileOnly());
		log.info(destination.toString());
		PDDocumentOutline outline = new PDDocumentOutline();
		destination.getDocumentCatalog().setDocumentOutline(outline);
		PDOutlineItem pagesOutline = new PDOutlineItem();
		pagesOutline.setTitle("All Pages");
		outline.addLast(pagesOutline);

		for (File pdf : files) {
			if (isPdf(pdf)) {
				log.info(pdf.toString());
				PDDocument sourceDoc = PDDocument.load(pdf, MemoryUsageSetting.setupTempFileOnly());

				appendDocument(destination, sourceDoc,
						pdf.getName().substring(pdf.getName().indexOf('-') + 1, pdf.getName().lastIndexOf(".")));

				sourceDoc.close();
			}
		}

		pagesOutline.openNode();
		outline.openNode();
		destination.getDocumentCatalog().setPageMode(PageMode.USE_OUTLINES);
		destination.save(f);
		destination.close();

		log.debug("Done!");
	}

	public void appendDocument(PDDocument destination, PDDocument source, String title) throws IOException {
		if (source.getDocument().isClosed()) {
			throw new IOException("Error: source PDF is closed.");
		}
		if (destination.getDocument().isClosed()) {
			throw new IOException("Error: destination PDF is closed.");
		}

		PDDocumentCatalog destCatalog = destination.getDocumentCatalog();
		PDDocumentCatalog srcCatalog = source.getDocumentCatalog();

		if (isDynamicXfa(srcCatalog.getAcroForm())) {
			throw new IOException("Error: can't merge source document containing dynamic XFA form content.");
		}

		PDDocumentInformation destInfo = destination.getDocumentInformation();
		PDDocumentInformation srcInfo = source.getDocumentInformation();
		mergeInto(srcInfo.getCOSObject(), destInfo.getCOSObject(), Collections.<COSName>emptySet());

		// use the highest version number for the resulting pdf
		float destVersion = destination.getVersion();
		float srcVersion = source.getVersion();

		if (destVersion < srcVersion) {
			destination.setVersion(srcVersion);
		}

		int pageIndexOpenActionDest = -1;
		if (destCatalog.getOpenAction() == null) {
			// PDFBOX-3972: get local dest page index, it must be reassigned after the page
			// cloning
			PDDestinationOrAction openAction = srcCatalog.getOpenAction();
			PDDestination openActionDestination;
			if (openAction instanceof PDActionGoTo) {
				openActionDestination = ((PDActionGoTo) openAction).getDestination();
			} else {
				openActionDestination = (PDDestination) openAction;
			}
			if (openActionDestination instanceof PDPageDestination) {
				PDPage page = ((PDPageDestination) openActionDestination).getPage();
				if (page != null) {
					pageIndexOpenActionDest = srcCatalog.getPages().indexOf(page);
				}
			}

			destCatalog.setOpenAction(srcCatalog.getOpenAction());
		}

		PDFCloneUtility cloner = new PDFCloneUtility(destination);

/*		try {
			PDAcroForm destAcroForm = destCatalog.getAcroForm();
			PDAcroForm srcAcroForm = srcCatalog.getAcroForm();

			if (destAcroForm == null && srcAcroForm != null) {
				destCatalog.getCOSObject().setItem(COSName.ACRO_FORM,
						cloner.cloneForNewDocument(srcAcroForm.getCOSObject()));

			} else {
				if (srcAcroForm != null) {
					mergeAcroForm(cloner, destAcroForm, srcAcroForm);
				}
			}
		} catch (IOException e) {
			// if we are not ignoring exceptions, we'll re-throw this
			if (!ignoreAcroFormErrors) {
				throw new IOException(e);
			}
		}
*/
		COSArray destThreads = (COSArray) destCatalog.getCOSObject().getDictionaryObject(COSName.THREADS);
		COSArray srcThreads = (COSArray) cloner
				.cloneForNewDocument(destCatalog.getCOSObject().getDictionaryObject(COSName.THREADS));
		if (destThreads == null) {
			destCatalog.getCOSObject().setItem(COSName.THREADS, srcThreads);
		} else {
			destThreads.addAll(srcThreads);
		}

		PDDocumentNameDictionary destNames = destCatalog.getNames();
		PDDocumentNameDictionary srcNames = srcCatalog.getNames();
		if (srcNames != null) {
			if (destNames == null) {
				destCatalog.getCOSObject().setItem(COSName.NAMES, cloner.cloneForNewDocument(srcNames));
			} else {
				cloner.cloneMerge(srcNames, destNames);
			}
		}

		PDDocumentNameDestinationDictionary destDests = destCatalog.getDests();
		PDDocumentNameDestinationDictionary srcDests = srcCatalog.getDests();
		if (srcDests != null) {
			if (destDests == null) {
				destCatalog.getCOSObject().setItem(COSName.DESTS, cloner.cloneForNewDocument(srcDests));
			} else {
				cloner.cloneMerge(srcDests, destDests);
			}
		}

		PDDocumentOutline destOutline = destCatalog.getDocumentOutline();
		PDDocumentOutline srcOutline = srcCatalog.getDocumentOutline();
		if (srcOutline != null) {
			if (destOutline == null || destOutline.getFirstChild() == null) {
				PDDocumentOutline cloned = new PDDocumentOutline(
						(COSDictionary) cloner.cloneForNewDocument(srcOutline));
				destCatalog.setDocumentOutline(cloned);
			} else {
				// search last sibling for dest, because /Last entry is sometimes wrong
				PDOutlineItem destLastOutlineItem = destOutline.getFirstChild();
				while (destLastOutlineItem.getNextSibling() != null) {
					destLastOutlineItem = destLastOutlineItem.getNextSibling();
				}
				for (PDOutlineItem item : srcOutline.children()) {
					// get each child, clone its dictionary, remove siblings info,
					// append outline item created from there
					COSDictionary clonedDict = (COSDictionary) cloner.cloneForNewDocument(item);
					clonedDict.removeItem(COSName.PREV);
					clonedDict.removeItem(COSName.NEXT);
					PDOutlineItem clonedItem = new PDOutlineItem(clonedDict);
					destLastOutlineItem.insertSiblingAfter(clonedItem);
					destLastOutlineItem = destLastOutlineItem.getNextSibling();
				}
			}
		}

		PageMode destPageMode = destCatalog.getPageMode();
		PageMode srcPageMode = srcCatalog.getPageMode();
		if (destPageMode == null) {
			destCatalog.setPageMode(srcPageMode);
		}

		COSDictionary destLabels = (COSDictionary) destCatalog.getCOSObject().getDictionaryObject(COSName.PAGE_LABELS);
		COSDictionary srcLabels = (COSDictionary) srcCatalog.getCOSObject().getDictionaryObject(COSName.PAGE_LABELS);
		if (srcLabels != null) {
			int destPageCount = destination.getNumberOfPages();
			COSArray destNums;
			if (destLabels == null) {
				destLabels = new COSDictionary();
				destNums = new COSArray();
				destLabels.setItem(COSName.NUMS, destNums);
				destCatalog.getCOSObject().setItem(COSName.PAGE_LABELS, destLabels);
			} else {
				destNums = (COSArray) destLabels.getDictionaryObject(COSName.NUMS);
			}
			COSArray srcNums = (COSArray) srcLabels.getDictionaryObject(COSName.NUMS);
			if (srcNums != null) {
				for (int i = 0; i < srcNums.size(); i += 2) {
					COSNumber labelIndex = (COSNumber) srcNums.getObject(i);
					long labelIndexValue = labelIndex.intValue();
					destNums.add(COSInteger.get(labelIndexValue + destPageCount));
					destNums.add(cloner.cloneForNewDocument(srcNums.getObject(i + 1)));
				}
			}
		}

		COSStream destMetadata = (COSStream) destCatalog.getCOSObject().getDictionaryObject(COSName.METADATA);
		COSStream srcMetadata = (COSStream) srcCatalog.getCOSObject().getDictionaryObject(COSName.METADATA);
		if (destMetadata == null && srcMetadata != null) {
			PDStream newStream = new PDStream(destination, srcMetadata.createInputStream(), (COSName) null);
			mergeInto(srcMetadata, newStream.getCOSObject(),
					new HashSet<COSName>(Arrays.asList(COSName.FILTER, COSName.LENGTH)));
			destCatalog.getCOSObject().setItem(COSName.METADATA, newStream);
		}

		COSDictionary destOCP = (COSDictionary) destCatalog.getCOSObject().getDictionaryObject(COSName.OCPROPERTIES);
		COSDictionary srcOCP = (COSDictionary) srcCatalog.getCOSObject().getDictionaryObject(COSName.OCPROPERTIES);
		if (destOCP == null && srcOCP != null) {
			destCatalog.getCOSObject().setItem(COSName.OCPROPERTIES, cloner.cloneForNewDocument(srcOCP));
		}

		mergeOutputIntents(cloner, srcCatalog, destCatalog);

		// merge logical structure hierarchy if logical structure information is
		// available in both source pdf and
		// destination pdf
		boolean mergeStructTree = false;
		int destParentTreeNextKey = -1;
		COSDictionary destParentTreeDict = null;
		COSDictionary srcParentTreeDict;
		COSArray destNumbersArray = null;
		COSArray srcNumbersArray = null;
		PDMarkInfo destMark = destCatalog.getMarkInfo();
		PDStructureTreeRoot destStructTree = destCatalog.getStructureTreeRoot();
		PDMarkInfo srcMark = srcCatalog.getMarkInfo();
		PDStructureTreeRoot srcStructTree = srcCatalog.getStructureTreeRoot();
		if (destStructTree != null) {
			PDNumberTreeNode destParentTree = destStructTree.getParentTree();
			destParentTreeNextKey = destStructTree.getParentTreeNextKey();
			if (destParentTree != null) {
				destParentTreeDict = destParentTree.getCOSObject();
				destNumbersArray = (COSArray) destParentTreeDict.getDictionaryObject(COSName.NUMS);
				if (destNumbersArray != null) {
					if (destParentTreeNextKey < 0) {
						destParentTreeNextKey = destNumbersArray.size() / 2;
					}
					if (destParentTreeNextKey > 0 && srcStructTree != null) {
						PDNumberTreeNode srcParentTree = srcStructTree.getParentTree();
						if (srcParentTree != null) {
							srcParentTreeDict = srcParentTree.getCOSObject();
							srcNumbersArray = (COSArray) srcParentTreeDict.getDictionaryObject(COSName.NUMS);
							if (srcNumbersArray != null) {
								mergeStructTree = true;
							}
						}
					}
				}
			}
			if (destMark != null && destMark.isMarked() && !mergeStructTree) {
				destMark.setMarked(false);
			}
			if (!mergeStructTree) {
				destCatalog.setStructureTreeRoot(null);
			}
		}

		Map<COSDictionary, COSDictionary> objMapping = new HashMap<COSDictionary, COSDictionary>();
		int pageIndex = 0;

		for (PDPage page : srcCatalog.getPages()) {
			PDPage newPage = new PDPage((COSDictionary) cloner.cloneForNewDocument(page.getCOSObject()));
			newPage.setCropBox(page.getCropBox());
			newPage.setMediaBox(page.getMediaBox());
			newPage.setRotation(page.getRotation());
			PDResources resources = page.getResources();

			if (resources != null) {
				// this is smart enough to just create references for resources that are used on
				// multiple pages
				newPage.setResources(new PDResources((COSDictionary) cloner.cloneForNewDocument(resources)));
			} else {
				newPage.setResources(new PDResources());
			}

			if (mergeStructTree) {
				updateStructParentEntries(newPage, destParentTreeNextKey);
				objMapping.put(page.getCOSObject(), newPage.getCOSObject());
				List<PDAnnotation> oldAnnots = page.getAnnotations();
				List<PDAnnotation> newAnnots = newPage.getAnnotations();
				for (int i = 0; i < oldAnnots.size(); i++) {
					objMapping.put(oldAnnots.get(i).getCOSObject(), newAnnots.get(i).getCOSObject());
				}
				// TODO update mapping for XObjects
			}

			destination.addPage(newPage);

			if (pageIndex == 0) {
				PDPageDestination dest = new PDPageFitWidthDestination();
				dest.setPage(newPage);
				PDOutlineItem bookmark = new PDOutlineItem();
				bookmark.setDestination(dest);
				bookmark.setTitle(title);
				destination.getDocumentCatalog().getDocumentOutline().getLastChild().addLast(bookmark);
			}

/*			if (pageIndex == pageIndexOpenActionDest) {
                // PDFBOX-3972: reassign the page.
                // The openAction is either a PDActionGoTo or a PDPageDestination
                PDDestinationOrAction openAction = destCatalog.getOpenAction();
                PDPageDestination pageDestination;
                
                if (destCatalog.getOpenAction() instanceof PDActionGoTo) {
                    pageDestination = (PDPageDestination) ((PDActionGoTo) openAction).getDestination();
                } else {
                    pageDestination = (PDPageDestination) openAction;
                }
                
                pageDestination.setPage(newPage);
            }
*/			
			++pageIndex;
		}

		if (mergeStructTree) {
			updatePageReferences(srcNumbersArray, objMapping);
			for (int i = 0; i < srcNumbersArray.size() / 2; i++) {
				destNumbersArray.add(COSInteger.get(destParentTreeNextKey + i));
				destNumbersArray.add(srcNumbersArray.getObject(i * 2 + 1));
			}
			destParentTreeNextKey += srcNumbersArray.size() / 2;
			destParentTreeDict.setItem(COSName.NUMS, destNumbersArray);
			PDNumberTreeNode newParentTreeNode = new PDNumberTreeNode(destParentTreeDict, COSBase.class);
			destStructTree.setParentTree(newParentTreeNode);
			destStructTree.setParentTreeNextKey(destParentTreeNextKey);

			COSDictionary kDictLevel0 = new COSDictionary();
			COSArray newKArray = new COSArray();
			COSArray destKArray = destStructTree.getKArray();
			COSArray srcKArray = srcStructTree.getKArray();
			if (destKArray != null && srcKArray != null) {
				updateParentEntry(destKArray, kDictLevel0);
				newKArray.addAll(destKArray);
				if (mergeStructTree) {
					updateParentEntry(srcKArray, kDictLevel0);
				}
				newKArray.addAll(srcKArray);
			}
			kDictLevel0.setItem(COSName.K, newKArray);
			kDictLevel0.setItem(COSName.P, destStructTree);
			kDictLevel0.setItem(COSName.S, new COSString(STRUCTURETYPE_DOCUMENT));
			destStructTree.setK(kDictLevel0);
		}
	}

	private void mergeAcroForm(PDFCloneUtility cloner, PDAcroForm destAcroForm, PDAcroForm srcAcroForm)
			throws IOException {
		List<PDField> srcFields = srcAcroForm.getFields();

		if (srcFields != null) {
			// if a form is merged multiple times using PDFBox the newly generated
			// fields starting with dummyFieldName may already exist. We need to determine
			// the last unique
			// number used and increment that.
			final String prefix = "dummyFieldName";
			final int prefixLength = prefix.length();

			for (PDField destField : destAcroForm.getFieldTree()) {
				String fieldName = destField.getPartialName();
				if (fieldName.startsWith(prefix)) {
					nextFieldNum = Math.max(nextFieldNum,
							Integer.parseInt(fieldName.substring(prefixLength, fieldName.length())) + 1);
				}
			}

			COSArray destFields = (COSArray) destAcroForm.getCOSObject().getItem(COSName.FIELDS);
			for (PDField srcField : srcAcroForm.getFieldTree()) {
				COSDictionary dstField = (COSDictionary) cloner.cloneForNewDocument(srcField.getCOSObject());
				// if the form already has a field with this name then we need to rename this
				// field
				// to prevent merge conflicts.
				if (destAcroForm.getField(srcField.getFullyQualifiedName()) != null) {
					dstField.setString(COSName.T, prefix + nextFieldNum++);
				}
				destFields.add(dstField);
			}
			destAcroForm.getCOSObject().setItem(COSName.FIELDS, destFields);
		}
	}

	public boolean isIgnoreAcroFormErrors() {
		return ignoreAcroFormErrors;
	}

	private void mergeInto(COSDictionary src, COSDictionary dst, Set<COSName> exclude) {
		for (Map.Entry<COSName, COSBase> entry : src.entrySet()) {
			if (!exclude.contains(entry.getKey()) && !dst.containsKey(entry.getKey())) {
				dst.setItem(entry.getKey(), entry.getValue());
			}
		}
	}

	private void mergeOutputIntents(PDFCloneUtility cloner, PDDocumentCatalog srcCatalog, PDDocumentCatalog destCatalog)
			throws IOException {
		List<PDOutputIntent> srcOutputIntents = srcCatalog.getOutputIntents();
		List<PDOutputIntent> dstOutputIntents = destCatalog.getOutputIntents();
		for (PDOutputIntent srcOI : srcOutputIntents) {
			String srcOCI = srcOI.getOutputConditionIdentifier();
			if (srcOCI != null && !"Custom".equals(srcOCI)) {
				// is that identifier already there?
				boolean skip = false;
				for (PDOutputIntent dstOI : dstOutputIntents) {
					if (dstOI.getOutputConditionIdentifier().equals(srcOCI)) {
						skip = true;
						break;
					}
				}
				if (skip) {
					continue;
				}
			}
			destCatalog.addOutputIntent(new PDOutputIntent((COSDictionary) cloner.cloneForNewDocument(srcOI)));
			dstOutputIntents.add(srcOI);
		}
	}

	private void updateStructParentEntries(PDPage page, int structParentOffset) throws IOException {
		page.setStructParents(page.getStructParents() + structParentOffset);
		List<PDAnnotation> annots = page.getAnnotations();
		List<PDAnnotation> newannots = new ArrayList<PDAnnotation>();
		for (PDAnnotation annot : annots) {
			annot.setStructParent(annot.getStructParent() + structParentOffset);
			newannots.add(annot);
		}
		page.setAnnotations(newannots);
	}

	private void updatePageReferences(COSDictionary parentTreeEntry, Map<COSDictionary, COSDictionary> objMapping) {
		COSBase page = parentTreeEntry.getDictionaryObject(COSName.PG);
		if (page instanceof COSDictionary && objMapping.containsKey(page)) {
			parentTreeEntry.setItem(COSName.PG, objMapping.get(page));
		}
		COSBase obj = parentTreeEntry.getDictionaryObject(COSName.OBJ);
		if (obj instanceof COSDictionary && objMapping.containsKey(obj)) {
			parentTreeEntry.setItem(COSName.OBJ, objMapping.get(obj));
		}
		COSBase kSubEntry = parentTreeEntry.getDictionaryObject(COSName.K);
		if (kSubEntry instanceof COSArray) {
			updatePageReferences((COSArray) kSubEntry, objMapping);
		} else if (kSubEntry instanceof COSDictionary) {
			updatePageReferences((COSDictionary) kSubEntry, objMapping);
		}
	}

	private void updatePageReferences(COSArray parentTreeEntry, Map<COSDictionary, COSDictionary> objMapping) {
		for (int i = 0; i < parentTreeEntry.size(); i++) {
			COSBase subEntry = parentTreeEntry.getObject(i);
			if (subEntry instanceof COSArray) {
				updatePageReferences((COSArray) subEntry, objMapping);
			} else if (subEntry instanceof COSDictionary) {
				updatePageReferences((COSDictionary) subEntry, objMapping);
			}
		}
	}

	private void updateParentEntry(COSArray kArray, COSDictionary newParent) {
		for (int i = 0; i < kArray.size(); i++) {
			COSBase subEntry = kArray.getObject(i);
			if (subEntry instanceof COSDictionary) {
				COSDictionary dictEntry = (COSDictionary) subEntry;
				if (dictEntry.getDictionaryObject(COSName.P) != null) {
					dictEntry.setItem(COSName.P, newParent);
				}
			}
		}
	}

	private boolean isDynamicXfa(PDAcroForm acroForm) {
		return acroForm != null && acroForm.xfaIsDynamic();
	}

	private void merged() throws IOException {
		File[] files = dir.listFiles();

		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		pdfMerger.setDestinationFileName(f.toString());
		int korak = 0;

		for (File pdf : files) {
			if (isPdf(pdf)) {
				log.info(pdf.toString());
				PDDocument pdfDoc = PDDocument.load(pdf, MemoryUsageSetting.setupTempFileOnly());

				removeField(pdfDoc);

				pdfDoc.save(pdf);
				pdfDoc.close();

				pdfMerger.addSource(pdf);

				if (korak == 0) {
					break;
				}
			}
		}

//		pdfMerger.addSource(new File("C:\\Users\\HrvojeBartolin\\Downloads\\out 1.8 GB.pdf"));
//		pdfMerger.addSource(new File("C:\\Users\\HrvojeBartolin\\Downloads\\out 512 MB.pdf"));

		pdfMerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

		log.debug("Done!");
	}

	private void removeField(PDDocument document) throws IOException {
		PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
		PDAcroForm acroForm = documentCatalog.getAcroForm();

		if (acroForm == null) {
			System.out.println("No form defined.");
			return;
		}

		List<PDField> targetFields = new ArrayList<PDField>();

		for (PDField field : acroForm.getFieldTree()) {
			log.info("\t - " + field.getFullyQualifiedName());
//            if (fullFieldName.equals(field.getFullyQualifiedName())) {
//                targetField = 
			targetFields.add(field);
//                break;
//            }
		}
		if (targetFields.isEmpty()) {
			System.out.println("Form does not contain field with given name.");
			return;
		}

		for (PDField targetField : targetFields) {
			PDNonTerminalField parentField = targetField.getParent();
			if (parentField != null) {
				List<PDField> childFields = parentField.getChildren();
				boolean removed = false;
				for (PDField field : childFields) {
					if (field.getCOSObject().equals(targetField.getCOSObject())) {
						removed = childFields.remove(field);
						parentField.setChildren(childFields);
						break;
					}
				}
				if (!removed)
					System.out
							.println("Inconsistent form definition: Parent field does not reference the target field.");
			} else {
				List<PDField> rootFields = acroForm.getFields();
				boolean removed = false;
				for (PDField field : rootFields) {
					if (field.getCOSObject().equals(targetField.getCOSObject())) {
						removed = rootFields.remove(field);
						break;
					}
				}
				if (!removed)
					System.out.println("Inconsistent form definition: Root fields do not include the target field.");
			}

			removeWidgets(targetField);
		}

//        return targetField;
	}

	void removeWidgets(PDField targetField) throws IOException {
		if (targetField instanceof PDTerminalField) {
			List<PDAnnotationWidget> widgets = ((PDTerminalField) targetField).getWidgets();
			for (PDAnnotationWidget widget : widgets) {
				PDPage page = widget.getPage();
				if (page != null) {
					List<PDAnnotation> annotations = page.getAnnotations();
					boolean removed = false;
					for (PDAnnotation annotation : annotations) {
						if (annotation.getCOSObject().equals(widget.getCOSObject())) {
							removed = annotations.remove(annotation);
							break;
						}
					}
					if (!removed)
						System.out.println(
								"Inconsistent annotation definition: Page annotations do not include the target widget.");
				} else {
					System.out.println("Widget annotation does not have an associated page; cannot remove widget.");
					// TODO: In this case iterate all pages and try to find and remove widget in all
					// of them
				}
			}
		} else if (targetField instanceof PDNonTerminalField) {
			List<PDField> childFields = ((PDNonTerminalField) targetField).getChildren();
			for (PDField field : childFields)
				removeWidgets(field);
		} else {
			System.out.println("Target field is neither terminal nor non-terminal; cannot remove widgets.");
		}
	}

	private boolean isPdf(File file) {
		String name = file.getName();
		int li = name.lastIndexOf(".");

		if (li != -1) {
			String extension = name.substring(li);

			if (extension.toLowerCase().equals(".pdf")) {
				return true;
			}
		}

		return false;
	}

	private void gen() throws IOException {
		PDDocument pd = new PDDocument();

		PDDocumentOutline outline = new PDDocumentOutline();
		pd.getDocumentCatalog().setDocumentOutline(outline);
		PDOutlineItem pagesOutline = new PDOutlineItem();
		pagesOutline.setTitle("All Pages");
		outline.addLast(pagesOutline);

		addPage(pd, "Jedan");
		addPage(pd, "Dva");

		pagesOutline.openNode();
		outline.openNode();
		pd.getDocumentCatalog().setPageMode(PageMode.USE_OUTLINES);

		pd.save(f);
		pd.close();

		log.debug("Done!");
	}

	private void addPage(PDDocument pd, String text) throws IOException {
		PDFont font = PDType1Font.HELVETICA_BOLD;

		PDPage page = new PDPage();
		pd.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(pd, page);
		contentStream.beginText();
		contentStream.setFont(font, 12);
		contentStream.newLineAtOffset(100, 700);
		contentStream.showText(text);
		contentStream.endText();
		contentStream.close();

		PDPageDestination dest = new PDPageFitWidthDestination();
		dest.setPage(page);
		PDOutlineItem bookmark = new PDOutlineItem();
		bookmark.setDestination(dest);
		bookmark.setTitle("Text - " + text.substring(0, 1));
		pd.getDocumentCatalog().getDocumentOutline().getLastChild().addLast(bookmark);
//        pagesOutline.addLast(bookmark);
	}
}

