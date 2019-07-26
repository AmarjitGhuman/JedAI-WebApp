package kr.di.uoa.gr.jedaiwebapp.utilities;

import java.util.List;

import org.scify.jedai.blockbuilding.ExtendedQGramsBlocking;
import org.scify.jedai.blockbuilding.ExtendedSortedNeighborhoodBlocking;
import org.scify.jedai.blockbuilding.ExtendedSuffixArraysBlocking;
import org.scify.jedai.blockbuilding.IBlockBuilding;
import org.scify.jedai.blockbuilding.LSHMinHashBlocking;
import org.scify.jedai.blockbuilding.LSHSuperBitBlocking;
import org.scify.jedai.blockbuilding.QGramsBlocking;
import org.scify.jedai.blockbuilding.SortedNeighborhoodBlocking;
import org.scify.jedai.blockbuilding.StandardBlocking;
import org.scify.jedai.blockbuilding.SuffixArraysBlocking;
import org.scify.jedai.blockprocessing.IBlockProcessing;
import org.scify.jedai.blockprocessing.blockcleaning.BlockFiltering;
import org.scify.jedai.blockprocessing.blockcleaning.ComparisonsBasedBlockPurging;
import org.scify.jedai.blockprocessing.blockcleaning.SizeBasedBlockPurging;
import org.scify.jedai.blockprocessing.comparisoncleaning.CanopyClustering;
import org.scify.jedai.blockprocessing.comparisoncleaning.CardinalityEdgePruning;
import org.scify.jedai.blockprocessing.comparisoncleaning.CardinalityNodePruning;
import org.scify.jedai.blockprocessing.comparisoncleaning.ComparisonPropagation;
import org.scify.jedai.blockprocessing.comparisoncleaning.ExtendedCanopyClustering;
import org.scify.jedai.blockprocessing.comparisoncleaning.ReciprocalCardinalityNodePruning;
import org.scify.jedai.blockprocessing.comparisoncleaning.ReciprocalWeightedNodePruning;
import org.scify.jedai.blockprocessing.comparisoncleaning.WeightedEdgePruning;
import org.scify.jedai.blockprocessing.comparisoncleaning.WeightedNodePruning;
import org.scify.jedai.entityclustering.CenterClustering;
import org.scify.jedai.entityclustering.ConnectedComponentsClustering;
import org.scify.jedai.entityclustering.CutClustering;
import org.scify.jedai.entityclustering.IEntityClustering;
import org.scify.jedai.entityclustering.MarkovClustering;
import org.scify.jedai.entityclustering.MergeCenterClustering;
import org.scify.jedai.entityclustering.RicochetSRClustering;
import org.scify.jedai.entityclustering.UniqueMappingClustering;
import org.scify.jedai.entitymatching.GroupLinkage;
import org.scify.jedai.entitymatching.IEntityMatching;
import org.scify.jedai.entitymatching.ProfileMatcher;
import org.scify.jedai.schemaclustering.AttributeNameClustering;
import org.scify.jedai.schemaclustering.AttributeValueClustering;
import org.scify.jedai.schemaclustering.HolisticAttributeClustering;
import org.scify.jedai.schemaclustering.ISchemaClustering;
import org.scify.jedai.utilities.enumerations.BlockBuildingMethod;
import org.scify.jedai.utilities.enumerations.RepresentationModel;
import org.scify.jedai.utilities.enumerations.SimilarityMetric;
import org.scify.jedai.utilities.enumerations.WeightingScheme;

import kr.di.uoa.gr.jedaiwebapp.models.Parameter;

public class DynamicMethodConfiguration {
	
	
	/**
     * Given a schema clustering method name and list of parameters, create an instance of the method configured with
     * the specified parameters. Used for manual configuration.
     *
     * @param methodName Name of the block cleaning method
     * @param parameters Parameters for method
     * @return Configured schema clustering method
     */
    public static ISchemaClustering configureSchemaClusteringMethod(String methodName, List<Parameter> parameters) {
        ISchemaClustering processingMethod = null;

        // Get appropriate processing method
        switch (methodName) {
            case JedaiOptions.ATTRIBUTE_NAME_CLUSTERING:
                processingMethod = new AttributeNameClustering(
                		RepresentationModel.valueOf((String) parameters.get(0).getValue()),
                		SimilarityMetric.valueOf((String) parameters.get(1).getValue())
                );
                break;
            case JedaiOptions.ATTRIBUTE_VALUE_CLUSTERING:
                processingMethod = new AttributeValueClustering(
                        RepresentationModel.valueOf((String) parameters.get(0).getValue()),
                        SimilarityMetric.valueOf((String) parameters.get(1).getValue())
                );
                break;
            case JedaiOptions.HOLISTIC_ATTRIBUTE_CLUSTERING:
                processingMethod = new HolisticAttributeClustering(
                		RepresentationModel.valueOf((String) parameters.get(0).getValue()),
                        SimilarityMetric.valueOf((String) parameters.get(1).getValue())
                );
                break;
        }

        return processingMethod;
    }
    
    
    

    
	
	
	
	/**
     * Given a Block Building method and a list of parameters, initialize and return that method with the given
     * parameters. Assumes the parameters are of the correct type (they are cast) and correct number.
     *
     * @param method     Method to initialize
     * @param parameters Parameter values
     * @return Block Building method configured with the given parameters
     */
    public static IBlockBuilding configureBlockBuildingMethod(BlockBuildingMethod method, List<Parameter> parameters) {
        
    	switch (method) {
        
    	case STANDARD_BLOCKING:
                return new StandardBlocking();
            case SUFFIX_ARRAYS:
                // todo: make sure these are correct
                return new SuffixArraysBlocking(
                        (int) parameters.get(0).getValue(), // Maximum Suffix Frequency
                        (int) parameters.get(1).getValue()  // Minimum Suffix Length
                );
            case Q_GRAMS_BLOCKING:
                return new QGramsBlocking(
                        (int) parameters.get(0).getValue()
                );
            case SORTED_NEIGHBORHOOD:
                return new SortedNeighborhoodBlocking(
                        (int) parameters.get(0).getValue()
                );
            case EXTENDED_SUFFIX_ARRAYS:
                // todo: make sure these are correct
                return new ExtendedSuffixArraysBlocking(
                        (int) parameters.get(0).getValue(), // Maximum Substring Frequency
                        (int) parameters.get(1).getValue()  // Minimum Substring Length
                );
            case EXTENDED_Q_GRAMS_BLOCKING:
                return new ExtendedQGramsBlocking(
                        (double) parameters.get(1).getValue(),
                        (int) parameters.get(0).getValue()
                );
            case EXTENDED_SORTED_NEIGHBORHOOD:
                return new ExtendedSortedNeighborhoodBlocking(
                        (int) parameters.get(0).getValue()
                );
            case LSH_SUPERBIT_BLOCKING:
                return new LSHSuperBitBlocking(
                        (int) parameters.get(0).getValue(),
                        (int) parameters.get(1).getValue()
                );
            case LSH_MINHASH_BLOCKING:
                return new LSHMinHashBlocking(
                        (int) parameters.get(0).getValue(),
                        (int) parameters.get(1).getValue()
                );
            default:
                return null;
        }
    }
    
    
    /**
     * Given a Block Cleaning method configuration object, create an instance of the method, configured with the
     * specified parameters. Assumes that configuration type is manual.
     *
     * @param methodName Name of the block cleaning method
     * @param parameters Parameters for method
     * @return Configured block cleaning method
     */
    public static IBlockProcessing configureBlockCleaningMethod(String methodName,List<Parameter> parameters) {
        IBlockProcessing processingMethod = null;

        // Get appropriate processing method
        switch (methodName) {
            case JedaiOptions.BLOCK_FILTERING:
                processingMethod = new BlockFiltering(
                        (double) parameters.get(0).getValue()
                );
                break;
            case JedaiOptions.SIZE_BASED_BLOCK_PURGING:
                processingMethod = new SizeBasedBlockPurging(
                        (double) parameters.get(0).getValue()
                );
                break;
            case JedaiOptions.COMPARISON_BASED_BLOCK_PURGING:
                processingMethod = new ComparisonsBasedBlockPurging(
                        (double) parameters.get(0).getValue()
                );
                break;
        }

        return processingMethod;
    }
    
    
    
    /**
     * Given a Comparison Cleaning method name and a list of parameters, initialize and return that method with these
     * parameters. Assumes the parameters are of the correct type (they are cast) and correct number.
     *
     * @param methodName Name of comparison cleaning method
     * @param parameters Parameters for method
     * @return Configured comparison cleaning method
     */
    public static IBlockProcessing configureComparisonCleaningMethod(String methodName,List<Parameter> parameters) {
            
		IBlockProcessing processingMethod = null;
		
		// Get appropriate processing method
		switch (methodName) {
			case JedaiOptions.COMPARISON_PROPAGATION:
				processingMethod = new ComparisonPropagation(); // Parameter-free
				break;
			
			
			case JedaiOptions.CARDINALITY_EDGE_PRUNING:
				processingMethod = new CardinalityEdgePruning(
						WeightingScheme.valueOf((String) parameters.get(0).getValue()));
				break;
			
			case JedaiOptions.CARDINALITY_NODE_PRUNING:
				processingMethod = new CardinalityNodePruning(
						WeightingScheme.valueOf((String) parameters.get(0).getValue()));
			
				break;
			
			case JedaiOptions.WEIGHED_EDGE_PRUNING:
				processingMethod = new WeightedEdgePruning(
						WeightingScheme.valueOf((String) parameters.get(0).getValue()));
				break;
			
			case JedaiOptions.WEIGHED_NODE_PRUNING:
				processingMethod = new WeightedNodePruning(
						WeightingScheme.valueOf((String) parameters.get(0).getValue()));
				break;
			
			case JedaiOptions.RECIPROCAL_CARDINALITY_NODE_PRUNING:
				processingMethod = new ReciprocalCardinalityNodePruning(
						WeightingScheme.valueOf((String) parameters.get(0).getValue()));
				break;
			
			case JedaiOptions.RECIPROCAL_WEIGHED_NODE_PRUNING:
				processingMethod = new ReciprocalWeightedNodePruning(
						WeightingScheme.valueOf((String) parameters.get(0).getValue()));
				break;
			
			case JedaiOptions.CANOPY_CLUSTERING:
				processingMethod = new CanopyClustering(
						(double) parameters.get(0).getValue(), // Inclusive threshold
						(double) parameters.get(1).getValue()  // Exclusive threshold
				);
				break;
			
			case JedaiOptions.CANOPY_CLUSTERING_EXTENDED:
				processingMethod = new ExtendedCanopyClustering(
						(int) parameters.get(0).getValue(), // Inclusive threshold
						(int) parameters.get(1).getValue()  // Exclusive threshold
				);
				break;
		}
		
		return processingMethod;
	}
    
    
    /**
     * Create and return an Entity Matching method, with the specified parameters
     *
     * @param emMethodName Name of the Entity Matching method
     * @param parameters   Parameters for the method
     * @return Entity Matching method configured with the given parameters
     */
    public static IEntityMatching configureEntityMatchingMethod(String emMethodName, List<Parameter> parameters) {
		
    	RepresentationModel rep;
		SimilarityMetric simMetric;
		
		switch (emMethodName) {
			
			case JedaiOptions.GROUP_LINKAGE:
				double simThr = (parameters != null) ? (double) parameters.get(2).getValue() : 0.5;
				
				rep = (parameters != null) ?
						RepresentationModel.valueOf((String) parameters.get(0).getValue()) : RepresentationModel.TOKEN_UNIGRAM_GRAPHS;
				simMetric = (parameters != null) ?
						SimilarityMetric.valueOf((String) parameters.get(1).getValue()) : SimilarityMetric.GRAPH_VALUE_SIMILARITY;
				
				return new GroupLinkage(simThr, rep, simMetric);
			
			case JedaiOptions.PROFILE_MATCHER:
				rep = (parameters != null) ?
						RepresentationModel.valueOf((String) parameters.get(0).getValue()) : RepresentationModel.TOKEN_UNIGRAM_GRAPHS;
				simMetric = (parameters != null) ?
						SimilarityMetric.valueOf((String) parameters.get(1).getValue()) : SimilarityMetric.GRAPH_VALUE_SIMILARITY;
				
				return new ProfileMatcher(rep, simMetric);
			
			default:
				
				return null;
		}
	}

    
    /**
     * Given a Entity Clustering method name and a list of parameters, initialize and return that method with these
     * parameters. Assumes the parameters are of the correct type (they are cast) and correct number.
     *
     * @param methodName Name of entity clustering method
     * @param parameters Parameters for method
     * @return Configured entity clustering method
     */
	public static IEntityClustering configureEntityClusteringMethod(String methodName, List<Parameter> parameters) {
		IEntityClustering ecMethod = null;
		
		// Get appropriate processing method
		switch (methodName) {
			case JedaiOptions.CENTER_CLUSTERING:
				ecMethod = new CenterClustering(
				(double) parameters.get(0).getValue()
				);
				break;
				
			case JedaiOptions.CONNECTED_COMPONENTS_CLUSTERING:
				ecMethod = new ConnectedComponentsClustering(
						(double) parameters.get(0).getValue());
				break;
				
			case JedaiOptions.CUT_CLUSTERING:
				ecMethod = new CutClustering(
					(double) parameters.get(1).getValue(),  // 1st parameter of CutClustering, but 2nd in JedAI-core
					(double) parameters.get(0).getValue());
				break;
				
			case JedaiOptions.MARKOV_CLUSTERING:
				ecMethod = new MarkovClustering(
					(double) parameters.get(1).getValue(),  // Cluster Threshold
					(double) parameters.get(2).getValue(),  // Matrix Similarity Threshold
					(int) parameters.get(3).getValue(),     // Similarity Checks Limit
					(double) parameters.get(0).getValue()   // Similarity Threshold
				);
				break;
				
			case JedaiOptions.MERGE_CENTER_CLUSTERING:
				ecMethod = new MergeCenterClustering(
						(double) parameters.get(0).getValue());
				break;
				
			case JedaiOptions.RICOCHET_SR_CLUSTERING:
				ecMethod = new RicochetSRClustering(
						(double) parameters.get(0).getValue());
				break;
				
			case JedaiOptions.UNIQUE_MAPPING_CLUSTERING:
				ecMethod = new UniqueMappingClustering(
						(double) parameters.get(0).getValue());
				break;
				
			default:
	        	System.out.println("ERROR: Entity clustering method does not exist: " + methodName);
	        	ecMethod = null;
		}
		
		return ecMethod;
	}

}